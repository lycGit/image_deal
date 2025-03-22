package com.lyc.ai.controller;

import com.lyc.ai.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public Message sendMessage(Message message) {
        System.out.println("Received message: " + message);
        message.setContent("收到的消息：" + message.getContent());
        return message;
    }

    @MessageMapping("/chat.join")
    @SendTo("/topic/public")
    public Message addUser(Message message, SimpMessageHeaderAccessor headerAccessor) {
        // 将用户名添加到 WebSocket 会话
        headerAccessor.getSessionAttributes().put("username", message.getSender());
        return message;
    }
}
