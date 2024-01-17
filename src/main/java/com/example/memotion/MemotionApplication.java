package com.example.memotion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication()
public class MemotionApplication {

    public static void main(String[] args) {
        SpringApplication.run(MemotionApplication.class, args);
    }

}
