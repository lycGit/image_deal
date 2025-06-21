package com.lyc.ai;
import javax.websocket.ClientEndpoint;
import javax.websocket.*;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URI;

@SpringBootApplication
@MapperScan("com.lyc.ai.mapper")
public class ImageRelayApp {
    public static Session session;

    public static void main(String[] args) {
        SpringApplication.run(ImageRelayApp.class, args);
    }
}
