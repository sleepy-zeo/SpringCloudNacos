package com.mogu.cache.front;

import com.mogu.cache.domain.User;
import com.mogu.cache.domain.UserDto;
import com.mogu.cache.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class IndexController {

    @Autowired
    private UserService userService;
    @Autowired
    private Environment environment;

    @GetMapping
    public String welcome() {
        return "Welcome to mogu cache world";
    }

    @RequestMapping("/get/{id}")
    public UserDto get(@PathVariable("id") int id) {
        User user = userService.getUser(id);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setPort(environment.getProperty("local.server.port"));

        return userDto;
    }

    @RequestMapping("/update/{id}")
    public UserDto update(@PathVariable("id") int id) {
        User user = new User();
        user.setId(id);
        user.setAge((int) (Math.random() * 15 + 15));
        user.setName(new Date().toString());
        userService.updateUser(user);

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setPort(environment.getProperty("local.server.port"));

        return userDto;
    }

    @RequestMapping("/insert/{id}")
    public UserDto insert(@PathVariable("id") int id) {
        User user = new User();
        user.setId(id);
        user.setAge((int) (Math.random() * 15 + 15));
        user.setName(new Date().toString());

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);
        userDto.setPort(environment.getProperty("local.server.port"));

        return userDto;
    }

    @RequestMapping("/del/{id}")
    public void del(@PathVariable("id") int id) {
        userService.delUser(id);
    }

}
