package com.sleepy.mp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.sleepy.mp"})
@EnableTransactionManagement
public class MPApplication {

    public static void main(String[] args) {
        SpringApplication.run(MPApplication.class, args);
    }

}
