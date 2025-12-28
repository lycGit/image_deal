package com.lyc.ai.controller;

import com.lyc.ai.pojo.entity.ExchangeCode;
import com.lyc.ai.service.ExchangeCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/exchange-code")
public class ExchangeCodeController {

    @Autowired
    private ExchangeCodeService exchangeCodeService;

    /**
     * 批量生成兑换码
     * @param params 包含count(数量)、points(积分)和validDays(有效期)参数
     * @return 生成结果
     */
    @PostMapping("/generate")
    public ResponseEntity<Map<String, Object>> batchGenerateCodes(@RequestBody Map<String, Integer> params) {
        try {
            int count = params.get("count");
            int points = params.get("points");
            int validDays = params.getOrDefault("validDays", 7); // 默认有效期为7天
            
            if (count <= 0 || points <= 0) {
                Map<String, Object> errorBody = new HashMap<>();
                errorBody.put("success", false);
                errorBody.put("message", "数量和积分必须大于0");
                return ResponseEntity.badRequest().body(errorBody);
            }
            
            Map<String, Object> result = exchangeCodeService.batchGenerateCodes(count, points, validDays);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("success", false);
            errorBody.put("message", "生成兑换码失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    /**
     * 获取一个未使用的兑换码，可以指定批次
     * @param batch 批次号（可选）
     * @return 兑换码信息
     */
    @GetMapping({"/get", "/get/{batch}"})
    public ResponseEntity<?> getExchangeCode(@PathVariable(required = false) String batch) {
        try {
            ExchangeCode exchangeCode;
            
            if (batch != null && !batch.isEmpty()) {
                // 获取指定批次的未使用兑换码
                exchangeCode = exchangeCodeService.getExchangeCodeByBatch(batch);
                if (exchangeCode == null) {
                    Map<String, Object> errorBody = new HashMap<>();
                    errorBody.put("success", false);
                    errorBody.put("message", "该批次没有可用的兑换码");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
                }
            } else {
                // 获取任意未使用的兑换码
                exchangeCode = exchangeCodeService.getExchangeCode();
                if (exchangeCode == null) {
                    Map<String, Object> errorBody = new HashMap<>();
                    errorBody.put("success", false);
                    errorBody.put("message", "没有可用的兑换码");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
                }
            }
            
            return ResponseEntity.ok(exchangeCode);
        } catch (Exception e) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("success", false);
            errorBody.put("message", "获取兑换码失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    /**
     * 消费兑换码积分
     * @param params 包含code(兑换码)和points(要消费的积分)参数
     * @return 消费结果
     */
    @PostMapping("/consume")
    public ResponseEntity<Map<String, Object>> consumePoints(@RequestBody Map<String, Object> params) {
        try {
            String code = (String) params.get("code");
            int points = (int) params.get("points");
            
            if (code == null || code.isEmpty() || points <= 0) {
                Map<String, Object> errorBody = new HashMap<>();
                errorBody.put("success", false);
                errorBody.put("message", "兑换码不能为空且积分必须大于0");
                return ResponseEntity.badRequest().body(errorBody);
            }
            
            Map<String, Object> result = exchangeCodeService.consumePoints(code, points);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("success", false);
            errorBody.put("message", "消费积分失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    /**
     * 根据兑换码获取详细信息
     * @param code 兑换码
     * @return 兑换码信息
     */
    @GetMapping("/info/{code}")
    public ResponseEntity<?> getExchangeCodeInfo(@PathVariable String code) {
        try {
            ExchangeCode exchangeCode = exchangeCodeService.getExchangeCodeInfo(code);
            
            if (exchangeCode == null) {
                Map<String, Object> errorBody = new HashMap<>();
                errorBody.put("success", false);
                errorBody.put("message", "兑换码不存在");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
            }
            
            return ResponseEntity.ok(exchangeCode);
        } catch (Exception e) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("success", false);
            errorBody.put("message", "获取兑换码信息失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }

    /**
     * 获取某一批次的所有兑换码
     * @param batch 批次
     * @return 兑换码列表
     */
    @GetMapping("/batch/{batch}")
    public ResponseEntity<?> getExchangeCodesByBatch(@PathVariable String batch) {
        try {
            List<ExchangeCode> exchangeCodes = exchangeCodeService.getExchangeCodesByBatch(batch);
            
            Map<String, Object> successBody = new HashMap<>();
            successBody.put("success", true);
            successBody.put("batch", batch);
            successBody.put("count", exchangeCodes.size());
            successBody.put("codes", exchangeCodes);
            return ResponseEntity.ok(successBody);
        } catch (Exception e) {
            Map<String, Object> errorBody = new HashMap<>();
            errorBody.put("success", false);
            errorBody.put("message", "获取批次兑换码失败: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
        }
    }
}