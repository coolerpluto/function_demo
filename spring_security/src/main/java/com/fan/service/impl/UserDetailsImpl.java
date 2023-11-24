package com.fan.service.impl;

import com.fan.entity.LoginInfo;
import com.fan.entity.LoginUser;
import com.fan.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginInfo user = userMapper.getByUserName(username);
        String encode = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encode);
        return new LoginUser(user);
    }
}
