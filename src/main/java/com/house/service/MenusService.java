package com.house.service;

import com.house.model.MenuTree;
import com.house.model.Menus;
import com.house.model.vo.MenusVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface MenusService extends IService<Menus> {

    List<MenuTree> buildMenuTree();

    List<MenusVO> getMenuList();

    Boolean updateMenusById(MenuTree menuTree);
}
