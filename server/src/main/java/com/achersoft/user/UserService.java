package com.achersoft.user;

import com.achersoft.user.dao.User;

public interface UserService {
    public User createUser(User user);
    public User getUser(int id);
    public User editUser(User user);
    public void deleteUser(int id);
}
