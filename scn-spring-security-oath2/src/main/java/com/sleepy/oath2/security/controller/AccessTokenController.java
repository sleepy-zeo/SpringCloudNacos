package com.sleepy.oath2.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AccessTokenController {

    @RequestMapping("")
    @ResponseBody
    public String accessToken(){
        return "ac";
    }
}
