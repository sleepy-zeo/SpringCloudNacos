package com.mogu.cache.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mogu.cache.domain.User;
import com.mogu.cache.service.UserService;
import com.mogu.cache.service.mapper.UserMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@CacheConfig(cacheNames = UserServiceImpl.CACHE_NAME)
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    public static final String CACHE_NAME = "user";

    @Cacheable(key = "'id'+#id")
    @Override
    public User getUser(int id) {
        return getOne(new QueryWrapper<User>().eq("id", id));
    }

    @CachePut(key = "'id'+#user.id")
    @Override
    public User updateUser(User user) {
        update(new UpdateWrapper<User>().ge("id", user.getId()).set("name", user.getName()).set("age", user.getAge()));
        return user;
    }

    @CachePut(key = "'id'+#user.id")
    @Override
    public User insertUser(User user) {
        save(user);
        return user;
    }

    @CacheEvict(key = "'id'+#id")
    @Override
    public void delUser(int id) {
        remove(new UpdateWrapper<User>().set("id", id));
    }
}
