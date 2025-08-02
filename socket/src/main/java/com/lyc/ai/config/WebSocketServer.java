package com.lyc.ai.config;

import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.github.pagehelper.util.StringUtil;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@ServerEndpoint(value="/webSocket/{userId}")
@Component
public class WebSocketServer {

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
            this.sendMessage(jsonObject, userId);
        } catch (Exception e){// 使用日志框架（如SLF4J）
            throw new RuntimeException("发送消息失败", e); // 或重新抛出
        }

    }
    @OnClose
    public void onClose(Session s) {
        if (onlineUsers.containsKey(userId)) {
            onlineUsers.remove(userId);
        }
    }
    @OnMessage
    public void onMessage(String msg) throws InterruptedException {
        System.out.println("从客服端接受的消息： "+msg);

         if (StringUtil.isNotEmpty(msg)) {
            try {
                // 1. 将传入的msg字符串解析为JSONObject
                JSONObject receivedJson = new JSONObject(msg);

                // 2. 创建新的JSONObject
                JSONObject jsonObject = new JSONObject();
                // 3. 使用keys()迭代器遍历所有键
                Iterator<String> keys = receivedJson.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
//                    jsonObject.put(key, receivedJson.get(key));
                    if (key.equals("msg")  && receivedJson.get(key).equals("ping")) {
                        jsonObject.put(key, "pong");
                    } else {
                        jsonObject.put(key, receivedJson.get(key));
                    }
                }
                String targetUserId = (String) receivedJson.get("targetUserId");
                // 4. 添加额外的userId字段
                jsonObject.put("userId", userId);
                // 5. 发送合并后的消息,"user_py_llm"
                this.sendMessage(jsonObject, targetUserId);

            } catch (Exception e) {
                // 处理JSON解析错误
                System.err.println("解析JSON消息出错: " + e.getMessage());

                // 如果解析失败，可以回退到原始方式
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msg", msg);
                jsonObject.put("userId", userId);
                this.sendMessage(jsonObject, userId);

            }
        }
    }

    public void send(String msg) {
        try {
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  public void sendMessage(JSONObject jsonObject, String targetUserId) {
        try {
            if (StringUtil.isNotEmpty(targetUserId) &&  onlineUsers.containsKey(targetUserId)) {
                onlineUsers.get(targetUserId).send(jsonObject.toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
