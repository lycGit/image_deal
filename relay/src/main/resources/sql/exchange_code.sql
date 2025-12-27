-- 创建兑换码表
CREATE TABLE IF NOT EXISTS exchange_code (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(20) NOT NULL UNIQUE COMMENT '兑换码',
    batch VARCHAR(20) NOT NULL COMMENT '批次号',
    total_points INT NOT NULL COMMENT '总积分',
    remaining_points INT NOT NULL COMMENT '剩余积分',
    status TINYINT NOT NULL COMMENT '状态：0-未获取，1-已获取，2-已使用',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    obtained_time DATETIME COMMENT '获取时间',
    INDEX idx_batch (batch),
    INDEX idx_status (status),
    INDEX idx_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='兑换码表';