package com.lyc.ai.service;

public interface AuthService {
    boolean authenticate(String password);
    void recordAuthLog(String password, boolean success);
}