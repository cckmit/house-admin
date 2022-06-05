package com.house.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.common.Constant;
import com.house.exception.CheckException;
import com.house.mapper.RoleMapper;
import com.house.model.Role;
import com.house.service.RoleService;
import com.house.service.UserService;
import com.house.utils.Query;
import com.house.utils.UUIDGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public IPage<Role> getRoleList(Map<String, Object> params, Role vo) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>()
                .like(StringUtils.isNotBlank(vo.getRoleName()), "ro.role_name", vo.getRoleName())
                .eq(StringUtils.isNotBlank(vo.getRoleId()), "ro.role_id", vo.getRoleId())
                .eq("ro.is_delete", Constant.NO_DELETE);

        IPage<Role> roleIPage = baseMapper.getRoleList(new Query<Role>().getPage(params), queryWrapper);
        List<Role> roleList = roleIPage.getRecords();
        // 处理权限数据
        for (Role role : roleList) {
            if (StringUtils.isNotBlank(role.getMenuInfo())) {
                List<Integer> idList = Stream.of(role.getMenuInfo().split(","))
                        .map(Integer::valueOf)
                        .collect(Collectors.toList());
                role.setMenu(idList);
            }
        }
        return roleIPage;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean setRoleStatus(Role role) {
        Role roleRes = this.getById(role.getRoleId());
        if (ObjectUtil.isNotEmpty(roleRes)) {
            return this.updateById(role);
        } else {
            throw new CheckException(String.format("未查询到角色ID为%s的数据;", role.getRoleId()));
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean addOrUpdate(Role role) {
        // 处理权限数据
        if (ObjectUtil.isNotEmpty(role.getMenu())) {
            String menuStr = role.getMenu().toString().replaceAll(", ", ",").replaceAll("[\\[\\]]", "");
            role.setMenuInfo(menuStr);
        }else {
            role.setMenuInfo(Constant.DASHBOAD_ID);
        }

        if (StringUtils.isNotBlank(role.getRoleId())) {
            // 更新代码
            return this.updateById(role);
        } else {
            // 新增代码
            role.setRoleId(UUIDGenerator.getNextId(Role.ROLE_PREFIX, baseMapper.getMaxNumByRole()));
            return this.save(role);
        }
    }

    @Override
    public Boolean removeRole(String roleId) {
        // 判断角色是否使用
        if (userService.checkUserRoleUse(roleId)) {
            throw new CheckException("角色正在使用,请勿删除！");
        } else {
            return this.removeById(roleId);
        }
    }

    @Override
    public Map<String, String> getRoleIdNameMap() {
        List<Role> roles = this.list();
        return roles.stream()
                .collect(Collectors.toMap(Role::getRoleId, Role::getRoleName, (k1, k2) -> k1));
    }

}




