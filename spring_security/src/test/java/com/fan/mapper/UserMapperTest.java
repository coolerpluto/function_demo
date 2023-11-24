package com.fan.mapper;

import com.fan.entity.LoginInfo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void f(){
        LoginInfo byUserName = userMapper.getByUserName("1");
        System.out.println(byUserName);
    }
}