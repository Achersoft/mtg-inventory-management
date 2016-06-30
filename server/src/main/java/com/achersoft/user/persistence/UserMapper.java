package com.achersoft.user.persistence;

import com.achersoft.security.dto.UserLoginRequest;
import com.achersoft.security.type.Privilege;
import com.achersoft.user.dao.User;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {   
    public void createUser(User user);
    public List<User> getUsers();
    public User getUser(int id);
    public User getUserFromName(String userName);
    public List<Privilege> getUserPrivileges(int id);
    public void addUserPrivileges(@Param("id") int id, @Param("privileges")List<Privilege> privileges);
    public void editUser(User user);
    public void deleteUser(int id);
    public boolean validateCredentials(UserLoginRequest request);
}

