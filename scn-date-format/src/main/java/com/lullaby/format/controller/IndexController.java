package com.lullaby.format.controller;

import com.lullaby.format.entity.Blog;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class IndexController {

    private Blog blog;

    @RequestMapping("")
    public Blog getBlog(){
        return blog;
    }

    @RequestMapping(value = "/blog",consumes = "application/json")
    public Blog setBlog(@RequestBody Blog blog){
        this.blog = blog;
        System.out.println(blog);
        return blog;
    }


}
