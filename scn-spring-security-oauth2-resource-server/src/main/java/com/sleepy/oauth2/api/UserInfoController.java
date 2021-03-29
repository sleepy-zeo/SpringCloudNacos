package com.sleepy.oauth2.api;

import com.sleepy.oauth2.vo.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/info")
public class UserInfoController {

    @ResponseBody
    @RequestMapping("")
    @PreAuthorize("#oauth2.hasScope('read')")
    User contacts() {
        return new User("whw", "17075166303");
    }
}
