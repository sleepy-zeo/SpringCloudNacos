package com.lullaby.druid.service.impl;

import com.lullaby.druid.entity.User;
import com.lullaby.druid.service.UserService;
import com.lullaby.druid.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public List<User> select() {
        return userMapper.select();
    }
}
