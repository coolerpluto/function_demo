package com.fan.pageHelper.service;

import com.fan.pageHelper.entity.SysMenu;
import com.fan.pageHelper.mapper.MenuMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<SysMenu> getMenu(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return menuMapper.menuList();
    }
}
