package com.lyc.ai.service;

import com.lyc.ai.pojo.entity.ExchangeCode;

import java.util.List;
import java.util.Map;

public interface ExchangeCodeService {
    /**
     * 批量生成兑换码
     * @param count 兑换码数量
     * @param points 每个兑换码对应的积分数
     * @param validDays 有效期（天）
     * @return 包含批次信息和生成的兑换码列表
     */
    Map<String, Object> batchGenerateCodes(int count, int points, int validDays);
    
    /**
     * 获取一个未使用的兑换码
     * @return 兑换码信息
     */
    ExchangeCode getExchangeCode();
    
    /**
     * 消费兑换码积分
     * @param code 兑换码
     * @param points 要消费的积分数
     * @return 消费结果
     */
    Map<String, Object> consumePoints(String code, int points);
    
    /**
     * 根据兑换码获取详细信息
     * @param code 兑换码
     * @return 兑换码信息
     */
    ExchangeCode getExchangeCodeInfo(String code);
    
    /**
     * 获取某一批次的所有兑换码
     * @param batch 批次
     * @return 兑换码列表
     */
    List<ExchangeCode> getExchangeCodesByBatch(String batch);
}