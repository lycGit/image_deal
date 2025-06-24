package com.lyc.ai.controller;

import javax.websocket.ClientEndpoint;
import javax.websocket.MessageHandler;
import javax.websocket.OnOpen;
import javax.websocket.Session;

@ClientEndpoint
public class MyEndpoint {
    private final String imageText;

    public MyEndpoint(String imageText) {
        this.imageText = imageText;
    }

    @OnOpen
    public void onOpen(Session session) {
        try {
            session.getBasicRemote().sendText(imageText);
            session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    System.out.println("收到来自服务端的消息: " + message);
                    // 这里可以添加更多的消息处理逻辑
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
