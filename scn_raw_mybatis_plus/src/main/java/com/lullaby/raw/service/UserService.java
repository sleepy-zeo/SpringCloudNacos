package com.lullaby.raw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lullaby.raw.domain.User;

public interface UserService extends IService<User> {

    void insert(User user);

    void deleteUser(int uid);

    void update(User user);

    User select(int uid);
}
