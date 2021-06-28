package com.mogu.cache.front;

import com.mogu.cache.domain.User;
import com.mogu.cache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public User test() {

        return userService.getUser(2);
    }

}
