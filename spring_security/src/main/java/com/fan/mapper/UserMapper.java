package com.fan.mapper;

import com.fan.entity.LoginInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    LoginInfo getByUserName(String userName);
}
