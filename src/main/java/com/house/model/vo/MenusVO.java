package com.house.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MenusVO {
    private String menuId;

    /**
     *
     */
    private String parentMenu;

    /**
     *
     */
    private String path;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String component;

    /**
     *
     */
    private String redirect;

    private MetaVO meta;

    private List<MenusVO> children;

}
