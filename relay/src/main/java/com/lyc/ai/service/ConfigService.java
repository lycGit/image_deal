package com.lyc.ai.service;

import java.util.Map;

public interface ConfigService {
    /**
     * 设置配置项
     * @param key 配置键
     * @param value 配置值
     * @param description 配置描述
     * @return 操作结果
     */
    Map<String, Object> setConfig(String key, String value, String description);
    
    /**
     * 获取所有配置项
     * @return 所有配置项的键值对
     */
    Map<String, Object> getAllConfigs();
    
    /**
     * 根据键获取配置值
     * @param key 配置键
     * @return 配置值
     */
    String getConfigValue(String key);
    
    /**
     * 删除配置项
     * @param key 配置键
     * @return 操作结果
     */
    Map<String, Object> deleteConfig(String key);
}