package com.sleepy.zeo.controller;

import com.sleepy.zeo.common.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/advice")
public class AdviceController {

    @RequestMapping("/ex")
    @ResponseBody
    String ex() throws Exception {
        throw new Exception("hello, this is Exception");
    }

    @RequestMapping("/np")
    @ResponseBody
    String np() throws NullPointerException {
        throw new NullPointerException("hello, this is NullPointerException");
    }

    @RequestMapping("/rt")
    @ResponseBody
    String rt() throws RuntimeException {
        throw new RuntimeException("hello, this is RuntimeException");
    }

    @RequestMapping("/nb")
    @ResponseBody
    String nb() throws NumberFormatException {
        throw new NumberFormatException("hello, this is NumberFormatException");
    }

    @RequestMapping(value = "/user", consumes = "application/json")
    @ResponseBody
    User user(@RequestBody User user) {
        System.out.println("user: " + user);
        return user;
    }
}
