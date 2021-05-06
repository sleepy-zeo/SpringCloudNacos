package com.lullaby.ssjr.controller;

import com.lullaby.ssjr.config.redis.JedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private JedisProperties jedisProperties;

    @RequestMapping("/test")
    public String test(){
        return "-->"+jedisProperties.getPassword() +" "+jedisProperties.getPool().getMinIdle();
    }
}
