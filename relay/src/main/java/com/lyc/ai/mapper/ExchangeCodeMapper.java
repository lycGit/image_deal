package com.lyc.ai.mapper;

import com.lyc.ai.pojo.entity.ExchangeCode;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExchangeCodeMapper {
    // 批量插入兑换码
    void batchInsertExchangeCodes(List<ExchangeCode> exchangeCodes);
    
    // 根据兑换码获取信息
    ExchangeCode getExchangeCodeByCode(String code);
    
    // 标记兑换码为已获取
    void markCodeAsObtained(String code);
    
    // 更新兑换码剩余积分
    void updateRemainingPoints(@Param("code") String code, @Param("newPoints") int newPoints);
    
    // 获取某一批次的所有兑换码
    List<ExchangeCode> getExchangeCodesByBatch(String batch);
    
    // 获取所有未获取的兑换码
    List<ExchangeCode> getUnobtainedExchangeCodes();
    
    // 获取指定批次的未获取兑换码
    List<ExchangeCode> getUnobtainedExchangeCodesByBatch(String batch);
    
    // 获取所有已获取但未使用完的兑换码
    List<ExchangeCode> getObtainedButNotUsedUpExchangeCodes();
    
    // 删除指定批次的所有兑换码
    int deleteExchangeCodesByBatch(String batch);
    
    // 获取所有批次
    List<String> getAllBatches();
    
    // 获取指定数量的未获取兑换码
    List<ExchangeCode> getUnobtainedExchangeCodesWithLimit(@Param("limit") int limit);
    
    // 获取指定批次的指定数量未获取兑换码
    List<ExchangeCode> getUnobtainedExchangeCodesByBatchWithLimit(@Param("batch") String batch, @Param("limit") int limit);
    
    // 获取指定批次未使用兑换码的数量
    int countUnobtainedExchangeCodesByBatch(@Param("batch") String batch);
}