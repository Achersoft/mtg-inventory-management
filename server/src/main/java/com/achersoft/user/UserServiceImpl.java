package com.achersoft.user;

import com.achersoft.exception.InvalidDataException;
import com.achersoft.security.helpers.PasswordHelper;
import com.achersoft.user.dao.User;
import com.achersoft.user.persistence.UserMapper;
import java.util.List;
import javax.inject.Inject;

public class UserServiceImpl implements UserService {

    private @Inject UserMapper userMapper;

    @Override
    public User createUser(User user) {
        if(user.getPassword() == null || user.getPassword().isEmpty())
            throw new InvalidDataException("A user password must be provided.");
            
        // Salt the user password before storage
        user.setPassword(PasswordHelper.generatePasswordHash(user.getPassword()));
       
        userMapper.createUser(user);
        User userFromName = userMapper.getUserFromName(user.getUsername());
        userMapper.addUserPrivileges(userFromName.getId(), user.getPrivileges());
        userFromName.setPrivileges(userMapper.getUserPrivileges(userFromName.getId()));
        
        return userFromName;
    }
    
    @Override
    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    @Override
    public User getUser(int id) {
        User user = userMapper.getUser(id);
        user.setPrivileges(userMapper.getUserPrivileges(id));
        return user;
    }

    @Override
    public User editUser(User user) {
        userMapper.editUser(user);
        return userMapper.getUser(user.getId());
    }

    @Override
    public void deleteUser(int id) {
        userMapper.deleteUser(id);
    }
}
