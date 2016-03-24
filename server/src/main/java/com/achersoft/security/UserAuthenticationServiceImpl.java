package com.achersoft.security;

import com.adobe.xmp.impl.Base64;
import com.achersoft.configuration.PropertiesManager;
import com.achersoft.exception.AuthenticationException;
import com.achersoft.exception.EstaffError;
import com.achersoft.security.authenticator.Authenticator;
import com.achersoft.security.dao.UserPrincipal;
import com.achersoft.security.providers.SignatureServiceProvider;
import com.achersoft.security.providers.UserPrincipalProvider;
import com.achersoft.user.dao.User;
import com.achersoft.security.dto.UserLoginRequest;
import com.achersoft.user.persistence.UserMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import javax.inject.Inject;

public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    
    private @Inject Authenticator authenticator;
    private @Inject PropertiesManager properties;
    private @Inject UserPrincipalProvider userPrincipalProvider;
    private @Inject UserMapper userMapper;
    private @Inject SignatureServiceProvider signatureServiceProvider;
    
    @Override
    public void login(UserLoginRequest request) {
        User user = userMapper.getUserFromName(request.getUserName());
        if(user == null)
            throw new AuthenticationException(EstaffError.USER_BAD_CREDENTIALS, "Invalid credentials.");
        if(user.getLocked()) {
            if(user.getLastAccessed() != null && user.getLastAccessed().before(new Date(new Date().getTime() - properties.getFailedLoginLockTimeout()))) {
                user.setLoginAttempts(0);
                user.setLocked(Boolean.FALSE);
            } else {
                throw new AuthenticationException(EstaffError.USER_BAD_CREDENTIALS, "User account has been locked.");
            }
        }

        user.setLastAccessed(new Date());
        userMapper.editUser(user);
        authenticator.validateUser(request);
        
        userPrincipalProvider.getUserPrincipal().setSessionId(UUID.randomUUID().toString());
        userPrincipalProvider.getUserPrincipal().setIat(new Date());
        userPrincipalProvider.getUserPrincipal().setExp(new Date(new Date().getTime() + properties.getSessionTimeout()));
        userPrincipalProvider.getUserPrincipal().setSub(Integer.toString(user.getId()));
        userPrincipalProvider.getUserPrincipal().setUserName(user.getUsername());
        userPrincipalProvider.getUserPrincipal().setName(user.getFirstName() + " " + user.getLastName());
        userPrincipalProvider.getUserPrincipal().setPrivileges(userMapper.getUserPrivileges(user.getId()));
        userPrincipalProvider.setAuthenticationToken(generateToken(userPrincipalProvider.getUserPrincipal()));
    }
    
   /* 
    @Override
    public void changeUserPassword(UserChangePassword userChangePassword) {
        EstaffUser user = userMapper.getUserFromName(userChangePassword.getUserName());
        userAuthenticators.get(user.getAuthenticatorId()).changeUserPassword(userChangePassword);
    }
    */
    @Override
    public UserPrincipal getUserPrincipal(String token) { 
        if(token != null && !token.isEmpty()) {
            String[] authToken = token.split("\\.");
            if(authToken.length == 3) {
                try {
                    return new ObjectMapper().readValue(Base64.decode(authToken[1]), UserPrincipal.class);
                } catch (IOException ex) {}
            }
        }
        return new UserPrincipal();
    }

    @Override
    public String authenticate(String token) throws AuthenticationException{
        UserPrincipal userPrincipal = getUserPrincipal(token);
        if(!token.equals(generateToken(userPrincipal))) {
            throw new AuthenticationException(EstaffError.INVALID_SESSION, "Invalid session.");
        }

        if(new Date().getTime() > userPrincipal.getExp().getTime()) {
            throw new AuthenticationException(EstaffError.SESSION_TIMEOUT, "User session has expired.");
        } 
        
        userPrincipal.setExp(new Date(new Date().getTime() + properties.getSessionTimeout()));
        return generateToken(userPrincipal);
    }

    private String generateToken(UserPrincipal userPrincipal) {
        try {
            String token = Base64.encode("{\"typ\":\"JWT\",\"alg\":\"HS256\"}");
            token += "." +  Base64.encode(new ObjectMapper().writeValueAsString(userPrincipal));
            return token + "." + new String(Base64.encode(signatureServiceProvider.sign(token.getBytes("UTF-8"))));
        } catch (Exception e) {
            throw new AuthenticationException(EstaffError.UNKNOWN_EXCEPTION, "Failed to generate authentication token");
        }
    }
}
