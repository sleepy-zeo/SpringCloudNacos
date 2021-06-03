package com.lullaby.mb.service.impl;

import com.lullaby.mb.domain.User;
import com.lullaby.mb.service.UserService;
import com.lullaby.mb.service.UserService2;
import com.lullaby.mb.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService2Impl implements UserService2 {

    @Autowired
    UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertUsers(User user) {
        userService.insertUserThrow(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, readOnly = true)
    public void readUser() {
        List<User> users = userMapper.getUsers();
        System.out.println("users: " + users);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<User> usersUpdate = userMapper.getUsers();
        System.out.println("users: " + usersUpdate);
        System.out.println("\n");
    }
}
