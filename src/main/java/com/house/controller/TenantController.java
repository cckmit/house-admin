package com.house.controller;

import com.house.common.bean.ResultBean;
import com.house.common.bean.PageResultBean;
import com.house.model.Tenant;
import com.house.service.TenantService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 房东管理
 */
@RestController
@RequestMapping("tenant")
public class TenantController {

    private TenantService tenantService;

    @Autowired
    public void setTenantService(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @Operation(summary ="获取租客列表")
    @GetMapping("/list")
    public PageResultBean<List<Tenant>> getHouseOwnerList( @RequestParam Map<String, Object> params, Tenant vo) {
        return PageResultBean.buildIPage(tenantService.getTenantList(params, vo));
    }

    @GetMapping(value = "getById/{id}")
    public ResultBean<Tenant> getById(@PathVariable("id") Integer id) {
        return ResultBean.ok(tenantService.getById(id));
    }

    @Operation(summary = "删除租客信息")
    @DeleteMapping("remove/{id}")
    public ResultBean<Boolean> removeTenantById(@PathVariable("id") String id) {
        return ResultBean.ok(tenantService.removeTenantById(id));
    }

    @Operation(summary = "新增或更新租客信息")
    @PostMapping("addOrUpdate")
    public ResultBean<Boolean> addOrUpdateTenant(@RequestBody Tenant Tenant) {
        return ResultBean.ok(tenantService.addOrUpdateTenant(Tenant));
    }

}

