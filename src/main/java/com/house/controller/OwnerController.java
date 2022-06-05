package com.house.controller;

import com.house.common.bean.PageResultBean;
import com.house.common.bean.ResultBean;
import com.house.model.HouseOwner;
import com.house.service.HouseOwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "房东模块")
@RestController
@RequestMapping("/owner")
public class OwnerController {
    private HouseOwnerService ownerService;

    @Autowired
    public void setOwnerService(HouseOwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Operation(summary = "获取房东列表")
    @GetMapping("/list")
    public PageResultBean<List<HouseOwner>> getHouseOwnerList(@RequestParam Map<String, Object> params, HouseOwner vo) {
        return PageResultBean.buildIPage(ownerService.getOwnerList(params, vo));
    }

    @GetMapping(value = "getById/{id}")
    public ResultBean<HouseOwner> getById(@PathVariable("id") Integer id) {
        return ResultBean.ok(ownerService.getById(id));
    }

    @Operation(summary = "删除房东")
    @DeleteMapping("remove/{id}")
    public ResultBean<Boolean> delOwner(@PathVariable("id") String id) {
        return ResultBean.ok(ownerService.removeOwnerById(id));
    }

    @Operation(summary = "新增或更新房东")
    @PostMapping("addOrUpdate")
    public ResultBean<HouseOwner> addOrUpdateOwner(@RequestBody HouseOwner owner) {
        return ResultBean.ok(ownerService.addOrUpdate(owner));
    }
}
