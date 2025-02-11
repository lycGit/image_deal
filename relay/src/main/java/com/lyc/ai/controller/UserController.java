package com.lyc.ai.controller;

import com.lyc.ai.pojo.entity.UserInfo;
import com.lyc.ai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/user/{userName}")
    public UserInfo findByUserName(@PathVariable("userName") String name) {
       return userService.findByName(name);
    }
}
