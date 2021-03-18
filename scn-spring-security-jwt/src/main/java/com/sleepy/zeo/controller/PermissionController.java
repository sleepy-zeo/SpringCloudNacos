package com.sleepy.zeo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perm")
public class PermissionController {

    @PreAuthorize("hasPermission('aa','bb')")
    @RequestMapping("/info1")
    public String info1() {
        return "info1";
    }

    @PreAuthorize("hasPermission('1','2','3')")
    @RequestMapping("/info2")
    public String info2() {
        return "info2";
    }
}
