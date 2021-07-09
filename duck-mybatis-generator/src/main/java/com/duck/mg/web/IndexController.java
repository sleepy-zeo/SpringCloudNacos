package com.duck.mg.web;

import com.duck.mg.domain.Topic;
import com.duck.mg.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @Autowired
    private TopicService topicService;

    @RequestMapping("")
    public Topic getTopic(int id) {
        return topicService.selectById(id);
    }

    @RequestMapping("/delete")
    public String deleteTopic(int id) {
        topicService.deleteById(id);
        return "success";
    }
}
