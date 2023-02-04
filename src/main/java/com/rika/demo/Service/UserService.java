package com.rika.demo.Service;

import com.rika.demo.entity.User;
import com.rika.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User getUserByUUID(String uuid) {
        return userMapper.getUserByUUID(uuid);
    }

    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    public void addNewUser(User user) {
        userMapper.addUser(user);
    }

    public void deleteUser(String uuid) {
        userMapper.deleteUser(uuid);
    }
}
