package com.lullaby.binding.valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public User insert(@Valid User user, BindingResult bindingResult) {
        log.info("user: " + user);
        if (bindingResult.hasErrors()) {
            log.error("error: " + bindingResult.getAllErrors());
            return null;
        }
        return user;
    }
}
