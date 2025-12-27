package com.lyc.ai.pojo.entity;

import java.util.Date;

public class ExchangeCode {
    private Long id;
    private String code;
    private String batch;
    private int totalPoints;
    private int remainingPoints;
    private int status; // 0: 未获取, 1: 已获取, 2: 已使用
    private Date createTime;
    private Date obtainedTime;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getRemainingPoints() {
        return remainingPoints;
    }

    public void setRemainingPoints(int remainingPoints) {
        this.remainingPoints = remainingPoints;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getObtainedTime() {
        return obtainedTime;
    }

    public void setObtainedTime(Date obtainedTime) {
        this.obtainedTime = obtainedTime;
    }
}