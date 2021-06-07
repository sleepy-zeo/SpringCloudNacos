package com.lullaby.raw.restful;

import com.lullaby.raw.domain.User;
import com.lullaby.raw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public String insert(User user) {
        userService.insert(user);
        return "success";
    }

    @GetMapping
    public User select(int uid) {
        return userService.select(uid);
    }

    @GetMapping("/batch")
    public Set<User> selectUsers() {

        List<Integer> uids = new ArrayList<>();
        uids.add(100);
        uids.add(101);

        System.out.println(uids);
        return userService.selectUsers(uids);
    }

    @GetMapping("/pair")
    public User selectUser() {
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("uid", 100);
        pairs.put("name", "steven");
        return userService.selectUser(pairs);
    }

    @GetMapping("/pair2")
    public Set<User> selectUser2() {
        Map<String, Object> pairs = new HashMap<>();
        pairs.put("uid", 1);
        pairs.put("name", "s");
        return userService.selectUsers_(pairs);
    }

    @PutMapping
    public String update(User user) {
        userService.update(user);
        return "success";
    }

    @DeleteMapping
    public String delete(User user) {
        userService.deleteUser(user.getUid());
        return "success";
    }
}
