package com.lyc.ai.service;

import com.lyc.ai.pojo.entity.UserInfo;

public interface UserService {
    UserInfo findByName(String name);
}
