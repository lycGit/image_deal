package com.lyc.ai.controller;

import com.lyc.ai.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * 设置配置项
     * @param params 包含key、value和description参数
     * @return 操作结果
     */
    @PostMapping("/set")
    public ResponseEntity<Map<String, Object>> setConfig(@RequestBody Map<String, String> params) {
        try {
            String key = params.get("key");
            String value = params.get("value");
            String description = params.get("description");
            
            if (key == null || key.isEmpty() || value == null) {
                Map<String, Object> errorBody = new HashMap<>();
                errorBody.put("success", false);
                errorBody.put("message", "配置键和值不能为空");
                return ResponseEntity.badRequest().body(errorBody);
            }
            
            Map<String, Object> result = configService.setConfig(key, value, description);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("success", false);
            errorBody.put("message", "设置配置失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    /**
     * 获取所有配置项
     * @return 所有配置项的键值对
     */
    @GetMapping("/get-all")
    public ResponseEntity<Map<String, Object>> getAllConfigs() {
        try {
            Map<String, Object> result = configService.getAllConfigs();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("success", false);
            errorBody.put("message", "获取配置失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    /**
     * 根据键获取配置值
     * @param key 配置键
     * @return 配置值
     */
    @GetMapping("/get/{key}")
    public ResponseEntity<Map<String, Object>> getConfigValue(@PathVariable String key) {
        try {
            if (key == null || key.isEmpty()) {
                Map<String, Object> errorBody = new HashMap<>();
                errorBody.put("success", false);
                errorBody.put("message", "配置键不能为空");
                return ResponseEntity.badRequest().body(errorBody);
            }
            
            String value = configService.getConfigValue(key);
            Map<String, Object> result = new HashMap<>();
            
            if (value != null) {
                result.put("success", true);
                result.put("key", key);
                result.put("value", value);
            } else {
                result.put("success", false);
                result.put("message", "未找到该配置项");
            }
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("success", false);
            errorBody.put("message", "获取配置值失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    /**
     * 删除配置项
     * @param key 配置键
     * @return 操作结果
     */
    @DeleteMapping("/delete/{key}")
    public ResponseEntity<Map<String, Object>> deleteConfig(@PathVariable String key) {
        try {
            if (key == null || key.isEmpty()) {
                Map<String, Object> errorBody = new HashMap<>();
                errorBody.put("success", false);
                errorBody.put("message", "配置键不能为空");
                return ResponseEntity.badRequest().body(errorBody);
            }
            
            Map<String, Object> result = configService.deleteConfig(key);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("success", false);
            errorBody.put("message", "删除配置失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }
}