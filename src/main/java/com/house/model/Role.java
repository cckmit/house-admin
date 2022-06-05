package com.house.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @TableName role
 */
@EqualsAndHashCode(callSuper = false)
@TableName(value = "role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseBean implements Serializable {
    /**
     * 角色ID
     */
    @TableId
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private int status;

    private String menuInfo;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    public String userId;

    @TableField(exist = false)
    public static final String ROLE_PREFIX = "RO";

    @TableField(exist = false)
    private List<Integer> menu;
}