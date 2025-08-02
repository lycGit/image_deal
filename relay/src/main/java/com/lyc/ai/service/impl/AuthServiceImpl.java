package com.lyc.ai.service.impl;

import com.lyc.ai.mapper.AuthLogMapper;
import com.lyc.ai.pojo.entity.AuthLog;
import com.lyc.ai.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service("authService")
public class AuthServiceImpl implements AuthService {
    // 预设的8位口令码
    private static final String VALID_PASSWORD = "12345678";

    @Autowired
    private AuthLogMapper authLogMapper;

    @Override
    public boolean authenticate(String password) {
        boolean isValid = VALID_PASSWORD.equals(password);
        recordAuthLog(password, isValid);
        return isValid;
    }

    @Override
    public void recordAuthLog(String password, boolean success) {
        AuthLog authLog = new AuthLog();
        authLog.setPassword(password);
        authLog.setSuccess(success);
        authLog.setCreateTime(new Date());
        authLogMapper.insertAuthLog(authLog);
    }
}