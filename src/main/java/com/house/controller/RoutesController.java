package com.house.controller;

import com.alibaba.fastjson.JSON;
import com.house.common.bean.ResultBean;
import com.house.service.RoutesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "路由接口")
@RestController
@RequestMapping("routers")
public class RoutesController {

    private RoutesService routesService;

    @Autowired
    public void setRoutesService(RoutesService routesService) {
        this.routesService = routesService;
    }

    @GetMapping("info")
    public ResultBean<Object> getRoutesList() {
        return ResultBean.ok(JSON.parse(JSON.toJSONString(routesService.getRoutes())));
    }
}
