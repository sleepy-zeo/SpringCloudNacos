package com.sleepy.amqp.controller;

import com.sleepy.amqp.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApController {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RequestMapping("/publish")
    public String publish(String exchange, String routing, String data) {
        System.out.println(exchange + "-" + routing + "-" + data);
        try {
            rabbitTemplate.convertAndSend(exchange, routing, new User(data));
        } catch (Throwable e) {
            return "failed: " + e.getMessage();
        }
        return "success";
    }

    @RequestMapping("/publish2")
    public String publish2(String exchange, String routing, String data) {
        System.out.println(exchange + "-" + routing + "-" + data);
        try {
            rabbitTemplate.convertAndSend(exchange, routing, data);
        } catch (Throwable e) {
            return "failed: " + e.getMessage();
        }
        return "success";
    }

    @RequestMapping("/publish3")
    public String publish3(String exchange, String routing, String data) {
        System.out.println(exchange + "-" + routing + "-" + data);
        try {
            rabbitTemplate.convertAndSend(exchange, routing, data.getBytes());
        } catch (Throwable e) {
            return "failed: " + e.getMessage();
        }
        return "success";
    }
}
