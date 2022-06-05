package com.house.controller;

import com.house.common.bean.ResultBean;
import com.house.common.bean.PageResultBean;
import com.house.model.Role;
import com.house.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "角色管理")
@RequestMapping("role")
@RestController
public class RoleController {

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary ="角色列表")
    @GetMapping("list")
    public PageResultBean<List<Role>> getRoleList( @RequestParam Map<String, Object> params,
                                                  Role vo) {
        return PageResultBean.buildIPage(roleService.getRoleList(params, vo));
    }

    @Operation(summary ="更新角色状态")
    @PostMapping("setRoleStatus")
    public ResultBean<Boolean> setRoleStatus(@RequestBody Role role) {
        return ResultBean.ok(roleService.setRoleStatus(role));
    }

    @Operation(summary = "保存或更新角色")
    @PostMapping("addOrUpdate")
    public ResultBean<Boolean> addOrUpdate(@RequestBody Role role) {
        return ResultBean.ok(roleService.addOrUpdate(role));
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("removeRoleById/{roleId}")
    public ResultBean<Boolean> removeRoleById(@PathVariable("roleId") String role) {
        return ResultBean.ok(roleService.removeRole(role));
    }


}
