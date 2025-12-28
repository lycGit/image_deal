package com.lyc.ai.mapper;

import com.lyc.ai.pojo.entity.Config;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ConfigMapper {
    // 设置配置（如果存在则更新，不存在则插入）
    void setConfig(@Param("key") String key, @Param("value") String value, @Param("description") String description);
    
    // 获取所有配置
    List<Config> getAllConfigs();
    
    // 根据键获取配置
    Config getConfigByKey(String key);
    
    // 删除配置
    void deleteConfig(String key);
}