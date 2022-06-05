package com.house.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.common.enums.ResultCode;
import com.house.exception.CheckException;
import com.house.mapper.MenusMapper;
import com.house.model.MenuTree;
import com.house.model.Role;
import com.house.service.RoleService;
import com.house.model.Menus;
import com.house.model.vo.MenusVO;
import com.house.model.vo.MetaVO;
import com.house.service.MenusService;
import com.house.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
@Slf4j
@Service
@Transactional
public class MenusServiceImpl extends ServiceImpl<MenusMapper, Menus> implements MenusService {

    @Autowired
    private RoleService roleService;

    /**
     * 使用递归方法建树
     */
    public static List<MenusVO> buildByRecursive(List<MenusVO> treeNodes) {
        List<MenusVO> trees = new ArrayList<>();
        for (MenusVO menusVO : treeNodes) {
            if ("main".equals(menusVO.getParentMenu())) {
                menusVO.setPath("/" + menusVO.getPath());
                trees.add(findChildren(menusVO, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     */
    public static MenusVO findChildren(MenusVO treeNode, List<MenusVO> treeNodes) {
        for (MenusVO it : treeNodes) {
            if (treeNode.getMenuId().equals(it.getParentMenu())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

    /**
     * 使用递归方法建树
     */
    public List<MenuTree> buildMenuTree() {
        List<MenuTree> treeNodes = baseMapper.getMenuListAll();
        List<MenuTree> trees = new ArrayList<>();
        for (MenuTree menusTree : treeNodes) {
            if (menusTree.getId() == 1) {
                menusTree.setDisabled(true);
            }
            if ("main".equals(menusTree.getParentMenu())) {
                trees.add(findChildrenMenuTree(menusTree, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     */
    private MenuTree findChildrenMenuTree(MenuTree treeNode, List<MenuTree> treeNodes) {
        for (MenuTree it : treeNodes) {
            if (treeNode.getMenuId().equals(it.getParentMenu())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildrenMenuTree(it, treeNodes));
            }
        }
        return treeNode;
    }

    @Override
    public List<MenusVO> getMenuList() {
        // 获取角色
        String roleId = UserContextHolder.getUserInfo().getRoleId();
        if (StringUtils.isBlank(roleId)) {
            throw new CheckException("未获取到登录信息，请联系管理员！");
        }
        Role role = roleService.getById(roleId);
        if (ObjectUtil.isEmpty(role)) {
            throw new CheckException(String.format("【%s】角色未查询到，请联系管理员！", role.getRoleName()));
        }
        String menuIds = role.getMenuInfo();
        if (StringUtils.isBlank(menuIds)) {
            return new ArrayList<>();
        }
        List<Integer> menuArray = Stream.of(menuIds.split(","))
                .map(Integer::valueOf)
                .distinct()
                .collect(Collectors.toList());
        // 增加DashBoad选中
        menuArray.add(1);
        LambdaQueryWrapper<Menus> menusLqw = Wrappers.<Menus>lambdaQuery()
                .in(Menus::getId, menuArray)
                .orderByAsc(Menus::getOrderNo);
        List<Menus> menuList = this.list(menusLqw);
        List<MenusVO> routeTreeList = menuList.stream()
                .map(s -> {
                    MenusVO menusVO = BeanUtil.toBean(s, MenusVO.class);
                    MetaVO metaVO = MetaVO.builder()
                            .icon(s.getMetaIcon())
                            .affix(ObjectUtil.equals(0, s.getMetaAffix()))
                            .title(s.getMetaTitle())
                            .frameSrc(s.getMetaFrameSrc())
                            .build();
                    menusVO.setMeta(metaVO);
                    return menusVO;
                })
                .collect(Collectors.toList());

        return buildByRecursive(routeTreeList);
    }

    @Override
    public Boolean updateMenusById(MenuTree menuTree) {
        log.info("menusVO=>{}", menuTree);
        if (ObjectUtil.isEmpty(menuTree)) {
            throw new CheckException(ResultCode.PARAM_IS_INVALID);
        }
        Menus menus = new Menus();
        menus.setId(menuTree.getId());
        menus.setComponent(menuTree.getComponent());
        menus.setMetaIcon(menuTree.getIcon());
        menus.setPath(menuTree.getPath());
        menus.setMetaAffix(menuTree.getAffix());
        menus.setMetaTitle(menuTree.getMenuName());
        menus.setOrderNo(menuTree.getOrderNo());

        // 更新
        if (null != menuTree.getId()) {
            this.updateById(menus);
        } else {
            // 新增
            this.save(menus);
        }
        return true;
    }


}




