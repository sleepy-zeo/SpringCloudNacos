package com.lullaby.ssjr.controller;

import com.lullaby.ssjr.config.redis.JedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForwardController {

    @RequestMapping("/exception")
    public String exception(){
        return "";
    }
}
