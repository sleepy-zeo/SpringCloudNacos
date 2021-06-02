package com.lullaby.mb.service.impl;

import com.lullaby.mb.domain.User;
import com.lullaby.mb.service.UserService;
import com.lullaby.mb.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUid(int uid) {
        return userMapper.getUser(uid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void insertUserThrow(User user) {
        userMapper.insertUser(user);
    }
}
