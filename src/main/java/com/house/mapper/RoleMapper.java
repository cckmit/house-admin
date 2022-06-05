package com.house.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.house.model.Role;
import org.apache.ibatis.annotations.Param;

/**
 * @Entity com.house.model.Role
 */
public interface RoleMapper extends BaseMapper<Role> {

    IPage<Role> getRoleList(IPage<Role> page, @Param(Constants.WRAPPER) QueryWrapper<Role> queryWrapper);

    Integer getMaxNumByRole();
}




