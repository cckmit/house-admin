package com.house.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MenuTree {
    private Integer id;

    private String icon;

    private String component;

    private String type;

    private String menuName;

    private String permission;

    private int orderNo;

    private Long createTime;

    private String status;

    private String parentMenu;

    private String menuId;

    private String path;

    private Integer affix;

    private List<MenuTree> children;

    private boolean disabled = false;
}
