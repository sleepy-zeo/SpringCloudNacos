package com.mogu.cache.front;

import com.mogu.cache.domain.User;
import com.mogu.cache.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get/{id}")
    public User get(@PathVariable("id") int id) {
        return userService.getUser(id);
    }

    @RequestMapping("/update/{id}")
    public User update(@PathVariable("id") int id) {
        User user = new User();
        user.setId(id);
        user.setName(new Date().toString());
        return userService.updateUser(user);
    }

    @RequestMapping("/insert/{id}")
    public User insert(@PathVariable("id") int id) {
        User user = new User();
        user.setId(id);
        user.setName(new Date().toString());
        return userService.insertUser(user);
    }

    @RequestMapping("/del/{id}")
    public void del(@PathVariable("id") int id) {
        userService.delUser(id);
    }

}
