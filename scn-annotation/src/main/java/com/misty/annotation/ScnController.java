package com.misty.annotation;

import com.misty.annotation.field.User;
import com.misty.annotation.method.ScnAnnotation;
import com.misty.annotation.type.PermissionCheck;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@PermissionCheck(resourceKey = "testKey")
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

    @GetMapping("/api/test")
    @ResponseBody
    public Object testPermissionCheck() {
        return "hello world";
    }

    @GetMapping("/gg")
    public String gg(HttpServletResponse response) throws IOException {
        return "forward:/hello";
    }

}
