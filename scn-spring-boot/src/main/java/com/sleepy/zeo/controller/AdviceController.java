package com.sleepy.zeo.controller;

import com.sleepy.zeo.common.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/advice")
public class AdviceController {

    @RequestMapping("/re")
    @ResponseBody
    String re() {
        return "hello world";
    }

    @RequestMapping(value = "/user",consumes = "application/json")
    @ResponseBody
    User user(@RequestBody User user) {
        System.out.println("user: "+user);
        return user;
    }
}
