package com.fan.entity;

import lombok.Data;
import lombok.ToString;

import java.util.Collection;

@Data
@ToString
public class LoginInfo {
    private Integer id;
    private String userName;
    private String password;
    private Collection<String> permissions;
}
