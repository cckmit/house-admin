package com.house.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 *
 */
public interface RoleService extends IService<Role> {

    IPage<Role> getRoleList(Map<String, Object> params, Role vo);

    Boolean setRoleStatus(Role role);

    Boolean addOrUpdate(Role role);

    Boolean removeRole(String roleId);

    Map<String, String> getRoleIdNameMap();
}
