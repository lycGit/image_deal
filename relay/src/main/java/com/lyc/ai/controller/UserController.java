package com.lyc.ai.controller;

import com.lyc.ai.ImageRelayApp;
import com.lyc.ai.pojo.entity.CreateImageInfo;
import com.lyc.ai.pojo.entity.UserInfo;
import com.lyc.ai.service.UserService;
import javax.websocket.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.websocket.ClientEndpoint;
import javax.websocket.*;
import java.net.URI;


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
}
