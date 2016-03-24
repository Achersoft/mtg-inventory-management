package com.achersoft.user.persistence;

import com.achersoft.security.dto.UserLoginRequest;
import com.achersoft.security.type.Privilege;
import com.achersoft.user.dao.User;
import java.util.List;

public interface UserMapper {   
    public void createUser(User user);
    public User getUser(int id);
    public User getUserFromName(String userName);
    public List<Privilege> getUserPrivileges(int id);
    public void editUser(User user);
    public void deleteUser(int id);
    public boolean validateCredentials(UserLoginRequest request);
}

