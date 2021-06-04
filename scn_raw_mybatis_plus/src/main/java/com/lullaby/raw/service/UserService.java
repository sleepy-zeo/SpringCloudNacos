package com.lullaby.raw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lullaby.raw.domain.User;

import java.util.List;
import java.util.Set;

public interface UserService extends IService<User> {

    void insert(User user);

    void deleteUser(int uid);

    void update(User user);

    User select(int uid);

    Set<User> selectUsers(List<Integer> uids);
}
