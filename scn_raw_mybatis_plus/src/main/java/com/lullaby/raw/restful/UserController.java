package com.lullaby.raw.restful;

import com.lullaby.raw.domain.User;
import com.lullaby.raw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public String insert(User user) {
        userService.insert(user);
        return "success";
    }

    @GetMapping
    public User select(int uid) {
        return userService.select(uid);
    }

    @PutMapping
    public String update(User user) {
        userService.update(user);
        return "success";
    }

    @DeleteMapping
    public String delete(User user) {
        userService.deleteUser(user.getUid());
        return "success";
    }
}
