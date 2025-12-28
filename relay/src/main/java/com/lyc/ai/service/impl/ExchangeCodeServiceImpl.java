package com.lyc.ai.service.impl;

import com.lyc.ai.mapper.ExchangeCodeMapper;
import com.lyc.ai.pojo.entity.ExchangeCode;
import com.lyc.ai.service.ExchangeCodeService;
import com.lyc.ai.util.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service("exchangeCodeService")
public class ExchangeCodeServiceImpl implements ExchangeCodeService {

    @Autowired
    private ExchangeCodeMapper exchangeCodeMapper;

    @Transactional
    @Override
    public Map<String, Object> batchGenerateCodes(int count, int points, int validDays) {
        // 生成唯一批次号
        String batch = UUID.randomUUID().toString().substring(0, 8);
        
        // 生成唯一兑换码
        Set<String> codeSet = CodeGenerator.generateUniqueCodes(count);
        
        // 创建ExchangeCode对象列表
        List<ExchangeCode> exchangeCodes = codeSet.stream().map(code -> {
            ExchangeCode exchangeCode = new ExchangeCode();
            exchangeCode.setCode(code);
            exchangeCode.setBatch(batch);
            exchangeCode.setTotalPoints(points);
            exchangeCode.setRemainingPoints(points);
            exchangeCode.setStatus(0); // 0: 未获取
            exchangeCode.setCreateTime(new Date());
            exchangeCode.setValidDays(validDays); // 设置有效期
            return exchangeCode;
        }).collect(Collectors.toList());
        
        // 批量插入数据库
        exchangeCodeMapper.batchInsertExchangeCodes(exchangeCodes);
        
        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("batch", batch);
        result.put("count", count);
        result.put("pointsPerCode", points);
        result.put("validDays", validDays);
        result.put("codes", codeSet);
        result.put("createTime", new Date());
        
        return result;
    }

    @Transactional
    @Override
    public ExchangeCode getExchangeCode() {
        // 获取一个未获取的兑换码
        List<ExchangeCode> unobtainedCodes = exchangeCodeMapper.getUnobtainedExchangeCodes();
        
        if (unobtainedCodes.isEmpty()) {
            return null;
        }
        
        // 获取第一个未获取的兑换码
        ExchangeCode exchangeCode = unobtainedCodes.get(0);
        
        // 标记为已获取
        exchangeCodeMapper.markCodeAsObtained(exchangeCode.getCode());
        
        // 更新内存中的对象状态
        exchangeCode.setStatus(1); // 1: 已获取
        exchangeCode.setObtainedTime(new Date());
        
        return exchangeCode;
    }

    @Transactional
    @Override
    public Map<String, Object> consumePoints(String code, int points) {
        Map<String, Object> result = new HashMap<>();
        
        // 验证兑换码是否存在
        ExchangeCode exchangeCode = exchangeCodeMapper.getExchangeCodeByCode(code);
        if (exchangeCode == null) {
            result.put("success", false);
            result.put("message", "兑换码不存在");
            return result;
        }
        
        // 验证兑换码是否已获取
        if (exchangeCode.getStatus() == 0) {
            result.put("success", false);
            result.put("message", "兑换码尚未被获取");
            return result;
        }
        
        // 验证积分是否足够
        if (exchangeCode.getRemainingPoints() < points) {
            result.put("success", false);
            result.put("message", "剩余积分不足");
            result.put("remainingPoints", exchangeCode.getRemainingPoints());
            return result;
        }
        
        // 计算剩余积分
        int newRemainingPoints = exchangeCode.getRemainingPoints() - points;
        
        // 更新剩余积分
        exchangeCodeMapper.updateRemainingPoints(code, newRemainingPoints);
        
        // 更新状态为已使用（如果积分用完）
        if (newRemainingPoints == 0) {
            exchangeCode.setStatus(2); // 2: 已使用
        }
        
        // 返回结果
        result.put("success", true);
        result.put("message", "积分消费成功");
        result.put("code", code);
        result.put("consumedPoints", points);
        result.put("remainingPoints", newRemainingPoints);
        result.put("totalPoints", exchangeCode.getTotalPoints());
        
        return result;
    }

    @Override
    public ExchangeCode getExchangeCodeInfo(String code) {
        ExchangeCode exchangeCode = exchangeCodeMapper.getExchangeCodeByCode(code);
        return exchangeCode;
    }

    @Override
    public List<ExchangeCode> getExchangeCodesByBatch(String batch) {
        return exchangeCodeMapper.getExchangeCodesByBatch(batch);
    }
}