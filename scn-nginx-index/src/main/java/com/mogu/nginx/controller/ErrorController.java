package com.mogu.nginx.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/404")
    public String error404() {
        return "error/404";
    }

    @RequestMapping("/405")
    public String error405() {
        return "error/405";
    }

    @RequestMapping("/500")
    public String error500() {
        return "error/500";
    }
}
