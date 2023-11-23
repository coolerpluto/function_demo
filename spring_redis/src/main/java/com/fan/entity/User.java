package com.fan.entity;

import lombok.Data;
import lombok.ToString;

@Data
public class User {

    private Integer id;
    private String name;
    private Teacher teacher;
}
