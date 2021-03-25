package com.sleepy.oauth2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScnController {

    @RequestMapping("")
    public User index() {
        return new User();
    }

    // 只允许这种格式
    // http://localhost:1506/test?name=xxx
    @RequestMapping("/test")
    public String test(User user) {
        return "hello scn: " + user;
    }
}
