package com.lullaby.error.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ScnController {

    @RequestMapping("")
    public String index() {
        return  "index";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        throw new IllegalArgumentException("ill");
    }
}
