package com.lullaby.error.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ScnController {

    @RequestMapping("")
    public String index() {
        return  "index";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(HttpServletResponse response) {
        return "example";
    }
}
