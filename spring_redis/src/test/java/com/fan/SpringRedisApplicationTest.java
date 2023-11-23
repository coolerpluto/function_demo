package com.fan;


import com.fan.entity.Teacher;
import com.fan.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;

@SpringBootTest
public class SpringRedisApplicationTest {


    @Autowired
    RedisTemplate redisTemplate;

    @Test
    public void a(){
//        redisTemplate.opsForValue().set("a", "1111");
//        System.out.println(redisTemplate.opsForValue().get("a"));
        Charset charset = Charset.forName("utf-8");
        System.out.println(charset);
    }

    @Test
    public void b(){
        User user = new User();
        user.setName("学生");
        Teacher teacher = new Teacher();
        teacher.setId(1);
        teacher.setName("老师");
        user.setTeacher(teacher);
        redisTemplate.opsForValue().set("user", user);
        System.out.println(redisTemplate.opsForValue().get("user"));
    }
}
