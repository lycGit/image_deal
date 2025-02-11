package com.lyc.ai.pojo.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName user_info
 */
@Data
public class UserInfo implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private Integer age;

    private static final long serialVersionUID = 1L;
}