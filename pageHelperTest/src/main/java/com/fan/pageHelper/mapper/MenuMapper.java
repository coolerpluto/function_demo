package com.fan.pageHelper.mapper;

import com.fan.pageHelper.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuMapper {

    public List<SysMenu> menuList();
}
