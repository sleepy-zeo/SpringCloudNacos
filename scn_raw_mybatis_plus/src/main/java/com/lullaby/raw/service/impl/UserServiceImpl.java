package com.lullaby.raw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lullaby.raw.domain.User;
import com.lullaby.raw.service.UserService;
import com.lullaby.raw.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, IService<User> {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insert(User user) {
        Optional<User> optionalUser = Optional.ofNullable(select(user.getUid()));
        if (optionalUser.equals(Optional.empty())) {
            userMapper.insert(user);
        }
    }

    @Override
    public void deleteUser(int uid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        userMapper.delete(queryWrapper);
    }

    @Override
    public void update(User user) {
        Optional.ofNullable(select(user.getUid()))
                .ifPresent(e -> userMapper.update(user, new QueryWrapper<User>().eq("uid", user.getUid())));
    }

    @Override
    public User select(int uid) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public Set<User> selectUsers(List<Integer> uids) {
        return userMapper.selectUsersWithList(uids);
    }

    @Override
    public User selectUser(Map<String, Object> pairs) {
        return userMapper.selectUser(pairs);
    }

    @Override
    public Set<User> selectUsers_(Map<String, Object> pairs) {
        return userMapper.selectUsersWithMap(pairs);
    }
}
