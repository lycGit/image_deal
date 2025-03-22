package com.lyc.ai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lyc.ai.mapper")
public class SocketApp {
    public static void main(String[] args) {
        SpringApplication.run(SocketApp.class, args);
    }
}
