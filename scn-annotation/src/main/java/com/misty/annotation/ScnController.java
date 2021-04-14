package com.misty.annotation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ScnController {

    @RequestMapping("/hello")
    @ResponseBody
    @LogAnnotation(desc = "my name is misty")
    public String hello() {
        System.out.println("hello");
        return "hello";
    }
}
