package com.duck.mg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.duck.mg.service.dao")
@SpringBootApplication
public class MgApplication {

    public static void main(String[] args) {
        SpringApplication.run(MgApplication.class, args);
    }
}
