package com.lullaby.druid.restful;

import com.alibaba.druid.pool.DruidDataSource;
import com.lullaby.druid.entity.User;
import com.lullaby.druid.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        log.info(dataSource.toString());
        if (dataSource instanceof DruidDataSource) {
            log.info(((DruidDataSource) dataSource).getInitialSize() + "");
            log.info(((DruidDataSource) dataSource).getMinIdle() + "");
            log.info(((DruidDataSource) dataSource).getMaxActive() + "");
        }

        return userService.select();
    }

    @GetMapping("/{uid}")
    public User getUser(@PathVariable("uid") int uid) {
        log.info(dataSource.toString());
        if (dataSource instanceof DruidDataSource) {
            log.info(((DruidDataSource) dataSource).getInitialSize() + "");
            log.info(((DruidDataSource) dataSource).getMinIdle() + "");
            log.info(((DruidDataSource) dataSource).getMaxActive() + "");
        }

        return userService.select(uid);
    }
}
