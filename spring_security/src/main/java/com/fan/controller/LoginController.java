package com.fan.controller;

import com.fan.entity.LoginInfo;
import com.fan.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.message.AuthException;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("login")
    public LoginUser login(@RequestBody LoginInfo loginInfo) throws AuthException {
        Authentication authentication = null;
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginInfo.getUserName(), loginInfo.getPassword());
        authentication = authenticationManager.authenticate(authenticationToken);
        if (authentication == null){
            throw new AuthException();
        }
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        return loginUser;
    }

    @GetMapping("hello")
    public String hello(){
        return "hello";
    }
}
