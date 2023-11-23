package com.fan.pageHelper.service;

import com.fan.pageHelper.entity.SysMenu;

import java.util.List;

public interface MenuService {
    List<SysMenu> getMenu(int pageNum, int pageSize);
}
