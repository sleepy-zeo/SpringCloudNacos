package com.mogu.cache.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mogu.cache.domain.User;
import com.mogu.cache.service.UserService;
import com.mogu.cache.service.mapper.UserMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Cacheable(cacheNames = "getUser", key = "'getUser'")
    @Override
    public User getUser(int id) {
        return getOne(new QueryWrapper<User>().eq("id",id));
    }
}
