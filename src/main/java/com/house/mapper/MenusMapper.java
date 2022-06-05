package com.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.house.model.MenuTree;
import com.house.model.Menus;

import java.util.List;

/**
 * @Entity com.house.model.Menus
 */
public interface MenusMapper extends BaseMapper<Menus> {

    List<MenuTree> getMenuListAll();

    int getMaxNum();
}




