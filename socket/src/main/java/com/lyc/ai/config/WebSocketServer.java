package com.lyc.ai.config;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.github.pagehelper.util.StringUtil;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value="/webSocket/{userId}")
@Component
public class WebSocketServer {
    // 新增静态缓存集合，用于存储在线用户
    private static ConcurrentHashMap<String, WebSocketServer> onlineUsers = new ConcurrentHashMap<>();
    private Session session;
    private String userId = "";

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) throws InterruptedException {
        this.session = session;
        this.userId = userId;
        if (onlineUsers.containsKey(userId)) {
            onlineUsers.remove(userId);
            onlineUsers.put(userId, this);
        } else {
            onlineUsers.put(userId, this);
        }
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("msg", "连接成功！");
            jsonObject.put("userId", userId);
            this.sendMessage(jsonObject);
        } catch (Exception e){

        }
    }

    @OnClose
    public void onClose(Session s) {
        // 从缓存中移除离开的用户
        if (onlineUsers.containsKey(userId)) {
            onlineUsers.remove(userId);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg", "用户离开");
                jsonObject.put("userId", userId);
                this.sendMessage(jsonObject);
            } catch (Exception e){

            }
        }
    }
    @OnMessage
    public void onMessage(String msg, Session session) throws InterruptedException {
        System.out.println("从客服端接受的消息： "+msg);
        if (StringUtil.isNotEmpty(msg)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg", msg);
                jsonObject.put("userId", userId);
                this.sendMessage(jsonObject);
            } catch (Exception e){

            }
        }
    }

    public void sendMessage(JSONObject jsonObject) {
        try {
            if (StringUtil.isNotEmpty(userId) &&  onlineUsers.containsKey(userId)) {
                onlineUsers.get(userId).send(jsonObject.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(String msg) {
        try {
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
