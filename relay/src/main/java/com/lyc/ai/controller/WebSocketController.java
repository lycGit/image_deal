package com.lyc.ai.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    // 处理客户端发送到 /app/hello 的消息
    @MessageMapping("/hello")
    @SendTo("/topic/greetings") // 将消息发送到 /topic/greetings
    public String greeting(String message) {
        return "Hello, " + message + "!";
    }
}
