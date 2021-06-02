package com.lullaby.mb.service;

import com.lullaby.mb.domain.User;

public interface UserService {

    User findUserByUid(int uid);

    void insertUser(User user);

    void insertUserThrow(User user);
}
