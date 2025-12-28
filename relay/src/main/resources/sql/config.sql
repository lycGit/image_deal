-- 创建配置表
CREATE TABLE IF NOT EXISTS config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    `key` VARCHAR(50) NOT NULL UNIQUE COMMENT '配置键',
    `value` VARCHAR(255) NOT NULL COMMENT '配置值',
    description VARCHAR(255) COMMENT '配置描述',
    create_time DATETIME NOT NULL COMMENT '创建时间',
    update_time DATETIME NOT NULL COMMENT '更新时间',
    INDEX idx_key (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='配置表';