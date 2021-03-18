package com.sleepy.zeo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PreAuthorize("hasAuthority('C')")
    @RequestMapping("/info1")
    public String info1() {
        return "info1";
    }

    @PreAuthorize("hasAuthority('U') AND hasAnyRole('D')")
    @RequestMapping("/info2")
    public String info2() {
        return "info2";
    }

    @PreAuthorize("hasAuthority('R')")
    @RequestMapping("/info3")
    public String info3() {
        return "info3";
    }

    @PreAuthorize("hasAuthority('D')")
    @RequestMapping("/info4")
    public String info4() {
        return "info4";
    }

    @PreAuthorize("hasAnyAuthority('C','R','D')")
    @RequestMapping("/info5")
    public String info5() {
        return "info5";
    }
}
