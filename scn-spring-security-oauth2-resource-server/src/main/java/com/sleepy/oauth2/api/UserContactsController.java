package com.sleepy.oauth2.api;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/contacts")
public class UserContactsController {

    @ResponseBody
    @RequestMapping("")
    String contacts() {
        return "aa;bb;cc";
    }
}
