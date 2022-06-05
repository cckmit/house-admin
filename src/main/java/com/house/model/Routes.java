package com.house.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName routes
 */
@TableName(value = "routes")
@Data
public class Routes implements Serializable {
    /**
     * 路由id
     */
    @TableId(type = IdType.AUTO)
    private Integer routeId;

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

}