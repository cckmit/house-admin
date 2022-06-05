package com.house.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName routes
 */
@Data
@Accessors(chain = true)
@TableName(value ="routes")
public class RoutesTree implements Serializable {

    /**
     *
     */
    private String id;

    /**
     * 图标
     */
    private String icon;

    /**
     * 前端模块
     */
    private String component;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 名称
     */
    private String menuName;

    /**
     *
     */
    private String permission;

    /**
     *
     */
    private Integer orderNo;

    /**
     *
     */
    private Long createtime;

    /**
     * 0-true 1-false
     */
    private String parentmenu;

    /**
     *
     */
    private Integer status;

    private List<RoutesTree> children;

}