package com.lullaby.auth.realm;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserDetailsToken extends UsernamePasswordToken {

    public UserDetailsToken(final String username, final String password) {
        super(username, password);
    }
}
