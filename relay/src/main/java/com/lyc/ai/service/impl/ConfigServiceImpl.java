package com.lyc.ai.service.impl;

import com.lyc.ai.mapper.ConfigMapper;
import com.lyc.ai.pojo.entity.Config;
import com.lyc.ai.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigMapper configMapper;

    @Override
    public Map<String, Object> setConfig(String key, String value, String description) {
        // 设置配置
        configMapper.setConfig(key, value, description);
        
        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("key", key);
        result.put("value", value);
        result.put("message", "配置设置成功");
        return result;
    }

    @Override
    public Map<String, Object> getAllConfigs() {
        // 获取所有配置
        List<Config> configs = configMapper.getAllConfigs();
        
        // 构建结果
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> configMap = new HashMap<>();
        
        for (Config config : configs) {
            Map<String, Object> configDetails = new HashMap<>();
            configDetails.put("value", config.getValue());
            configDetails.put("description", config.getDescription());
            configDetails.put("createTime", config.getCreateTime());
            configDetails.put("updateTime", config.getUpdateTime());
            configMap.put(config.getKey(), configDetails);
        }
        
        result.put("success", true);
        result.put("configs", configMap);
        return result;
    }

    @Override
    public String getConfigValue(String key) {
        // 根据键获取配置
        Config config = configMapper.getConfigByKey(key);
        return config != null ? config.getValue() : null;
    }

    @Override
    public Map<String, Object> deleteConfig(String key) {
        // 删除配置
        configMapper.deleteConfig(key);
        
        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("key", key);
        result.put("message", "配置删除成功");
        return result;
    }
}