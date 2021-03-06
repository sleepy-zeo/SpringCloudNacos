package com.lullaby.mb.restful;

import com.lullaby.mb.domain.User;
import com.lullaby.mb.service.UserService;
import com.lullaby.mb.service.UserService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService2 userService2;

    @Autowired
    private UserService userService;

    @RequestMapping("/{uid}")
    User fetchUser(@PathVariable("uid") int uid) {
        return userService.findUserByUid(uid);
    }

    @PostMapping
    String insertUser(User user) {
        System.out.println("user: " + user);
        userService.insertUser(user);
        return "success";
    }

}
