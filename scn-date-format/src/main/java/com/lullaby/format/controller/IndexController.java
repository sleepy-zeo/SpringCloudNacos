package com.lullaby.format.controller;

import com.lullaby.format.entity.Blog;
import com.lullaby.format.entity.Blog2;
import com.lullaby.format.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    private Blog blog;

    @RequestMapping("")
    public Blog getBlog() {
        return blog;
    }

    @RequestMapping(value = "/blog", consumes = "application/json")
    public Blog setBlog(@RequestBody Blog blog) {
        this.blog = blog;
        System.out.println(blog);
        return blog;
    }

    @RequestMapping("/user")
    public User getUser() {
        User user = new User();
        user.setAvatar("https://avatar.png");
        user.setEmail("steven@outlook.com");
        user.setUid(15252021512L);
        user.setUsername("steven");
        user.setNickname("gigi");
        user.setPassword("@#$qwer");
        user.setLastIp("127.123.0.1");
        user.setDataStatus(false);


        return user;
    }

    @RequestMapping("/blog2")
    public Blog2 blog2() {
        Blog2 blog2 = new Blog2();
        blog2.setValue(new Long[]{1L});
        blog2.setBlogId(10012L);
        blog2.setAppreciation(true);
        blog2.setCommentEnabled(true);
        blog2.setContent("This is a ....");
        blog2.setFirstPicture("first pic");
        blog2.setFlag("flag");
        blog2.setThumbs(120);
        blog2.setPublished(true);
        blog2.setRecommend(false);
        blog2.setShareStatement("no share");
        blog2.setTitle("Title scn");
        blog2.setViews(10002);
        blog2.setTypeId(2L);
        blog2.setDescription("this is ...");
        return blog2;
    }

}
