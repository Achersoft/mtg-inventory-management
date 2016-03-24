package com.achersoft.user;

import com.achersoft.user.dao.User;
import com.achersoft.user.persistence.UserMapper;
import javax.inject.Inject;

public class UserServiceImpl implements UserService {

    private @Inject UserMapper userMapper;

    @Override
    public User createUser(User user) {
        userMapper.createUser(user);
        return userMapper.getUserFromName(user.getUsername());
    }

    @Override
    public User getUser(int id) {
        return userMapper.getUser(id);
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
