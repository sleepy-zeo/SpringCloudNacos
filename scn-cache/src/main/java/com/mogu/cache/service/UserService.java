package com.mogu.cache.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mogu.cache.domain.User;

public interface UserService extends IService<User> {

    User getUser(int id);

    User updateUser(User user);

    User insertUser(User user);

    void delUser(int id);
}
