package com.sleepy.el;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ELApplication {

    public static void main(String[] args) {
        SpringApplication.run(ELApplication.class, args);
    }
}
