package com.lyc.ai.mapper;

import com.lyc.ai.pojo.entity.UserInfo;
import org.apache.ibatis.annotations.Param;

/**
* @author lyc
* @description 针对表【user_info】的数据库操作Mapper
* @createDate 2025-02-10 02:42:13
* @Entity com.lyc.ai.pojo.entity.UserInfo
*/
public interface UserInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(UserInfo record);

    int insertSelective(UserInfo record);

    UserInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserInfo record);

    int updateByPrimaryKey(UserInfo record);

    UserInfo findUserInfoByName(@Param("name") String userName);

}
