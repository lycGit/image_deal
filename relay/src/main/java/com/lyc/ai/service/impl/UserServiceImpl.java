package com.lyc.ai.service.impl;

import com.lyc.ai.mapper.UserInfoMapper;
import com.lyc.ai.pojo.entity.UserInfo;
import com.lyc.ai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Override
    public UserInfo findByName(String name) {
        return userInfoMapper.findUserInfoByName(name);
    }
}
