package com.lullaby.mb.service.impl;

import com.lullaby.mb.domain.User;
import com.lullaby.mb.service.UserService;
import com.lullaby.mb.service.UserService2;
import com.lullaby.mb.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void readUser() {
        List<User> users = userMapper.getUsers();
        System.out.println("users: " + users);

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("============");

        List<User> users2 = userMapper.getUsers();
        System.out.println("users: " + users2);
        System.out.println("\n\n\n");
    }
}
