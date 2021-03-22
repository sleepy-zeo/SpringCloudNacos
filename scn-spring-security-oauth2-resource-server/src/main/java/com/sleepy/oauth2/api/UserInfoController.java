package com.sleepy.oauth2.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/info")
public class UserInfoController {

    @ResponseBody
    @RequestMapping("")
    String contacts() {
        return "Sleepy Zeo; 27; male";
    }
}
