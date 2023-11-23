package com.fan.pageHelper.controller;

import com.fan.pageHelper.entity.SysMenu;
import com.fan.pageHelper.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/page")
public class PageTestController {

    @Autowired
    private MenuService menuService;

    @RequestMapping("/page/{pageNum}/{pageSize}")
    public List<SysMenu> page(@PathVariable int pageNum, @PathVariable int pageSize){
        List<SysMenu> menu = menuService.getMenu(pageNum,pageSize);
        return menu;
    }
}
