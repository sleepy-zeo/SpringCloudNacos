package com.lullaby.binding.validated;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/user2")
public class UserController2 {

    @GetMapping("/group")
    public User insert(@Validated(value = {Insert.class}) User user, BindingResult bindingResult) {
        log.info("user: " + user);
        if (bindingResult.hasErrors()) {
            log.error("error: " + bindingResult.getAllErrors());
            return null;
        }
        return user;
    }

    @GetMapping("/common")
    public User select(@Validated User user, BindingResult bindingResult) {
        log.info("user: " + user);
        if (bindingResult.hasErrors()) {
            log.error("error: " + bindingResult.getAllErrors());
            return null;
        }
        return user;
    }
}
