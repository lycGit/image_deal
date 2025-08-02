package com.lyc.ai.controller;

import com.lyc.ai.ImageRelayApp;
import com.lyc.ai.pojo.entity.CreateImageInfo;
import com.lyc.ai.pojo.entity.UserInfo;
import com.lyc.ai.service.UserService;
import com.lyc.ai.service.AuthService;
import javax.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.websocket.ClientEndpoint;
import javax.websocket.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/user/{userName}")
    public UserInfo findByUserName(@PathVariable("userName") String name) {
       return userService.findByName(name);
    }

    @GetMapping("/createImage/{imageText}") 
     public CreateImageInfo createByImageText(@PathVariable("imageText") String imageText) { 
         CreateImageInfo info = new CreateImageInfo(); 
         info.imageUrl = "http://localhost:8091/api/files/download/901a1056-3766-4af3-a3d5-978f0340b8d6_test_header_image.jpg"; 
         info.description = "收到的文生图描述文字：" + imageText;

        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            Session session = container.connectToServer(new MyEndpoint(imageText), URI.create("ws://120.27.130.190:8092/webSocket"));
        } catch (Exception e) {
            e.printStackTrace();
        }

         return info; 
     }

    @GetMapping("/klImage/{imageText}")
    public CreateImageInfo createByKLImageText(@PathVariable("imageText") String imageText) {
        CreateImageInfo info = new CreateImageInfo();
        info.imageUrl = "http://localhost:8091/api/files/download/e9d4b8d8-3a2e-4367-a51b-f17772edc9d2_output1.png";
        info.description = "收到的文生图描述文字555：" + imageText;
        return info;
    }

    @Autowired
    private AuthService authService;

    @GetMapping("/auth/{password}")
    public Map<String, Object> authenticate(@PathVariable("password") String password) {
        Map<String, Object> result = new HashMap<>();
        if (password.length() != 8) {
            result.put("success", false);
            result.put("message", "口令码必须为8位");
            authService.recordAuthLog(password, false);
            return result;
        }

        boolean isAuthenticated = authService.authenticate(password);
        if (isAuthenticated) {
            result.put("success", true);
            result.put("message", "鉴权成功，欢迎访问");
        } else {
            result.put("success", false);
            result.put("message", "口令码错误，拒绝访问");
        }
        return result;
    }
}
