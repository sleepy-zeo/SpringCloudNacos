package com.misty.annotation;

import com.misty.annotation.field.User;
import com.misty.annotation.method.ScnAnnotation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ScnController {

    @RequestMapping("/hello")
    @ResponseBody
    @ScnAnnotation(desc = "my name is misty")
    public String hello() {
        System.out.println("hello");
        return "hello";
    }

    @RequestMapping("/hello2")
    @ResponseBody
    public String hello2(@Validated User user) {
        System.out.println("hello: " + user);
        return "hello";
    }
}
