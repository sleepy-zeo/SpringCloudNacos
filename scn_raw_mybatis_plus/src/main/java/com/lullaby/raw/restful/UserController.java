package com.lullaby.raw.restful;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lullaby.raw.domain.User;
import com.lullaby.raw.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(tags = "user操作")
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

    @GetMapping("/test")
    public PageInfo<?> test(@PageableDefault Pageable pageable) {
        System.out.println("--" + pageable);

        String order;
        order = pageable.getSort().toString().replace(":", "");
        if ("UNSORTED".equals(order)) {
            order = "uid desc";
        }
        PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize(), order);
        List<User> userList = userService.list();
        PageInfo<?> pageInfo = new PageInfo<>(userList);

        return pageInfo;
    }
}
