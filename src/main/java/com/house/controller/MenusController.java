package com.house.controller;

import com.alibaba.fastjson.JSON;
import com.house.common.bean.ResultBean;
import com.house.model.MenuTree;
import com.house.service.MenusService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "菜单接口")
@RequestMapping("menu")
@RestController
public class MenusController {

    private MenusService menusService;

    @Autowired
    public void setMenusService(MenusService menusService) {
        this.menusService = menusService;
    }

    @GetMapping("list")
    public ResultBean<Object> getMenuList() {
        return ResultBean.ok(JSON.parse(JSON.toJSONString(menusService.getMenuList())));
    }

    @GetMapping("listAll")
    public ResultBean<Object> getMenuTreeList() {
        return ResultBean.ok(JSON.parse(JSON.toJSONString(menusService.buildMenuTree())));
    }

    @PostMapping("addOrUpdate")
    public ResultBean<Boolean> updateMenuById(@RequestBody MenuTree menus) {
        return ResultBean.ok(menusService.updateMenusById(menus));
    }

}
