package com.house.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName menus
 */
@TableName(value ="menus")
@Data
public class Menus implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
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

    /**
     * 
     */
    private String metaTitle;

    /**
     * 0-true 1-false
     */
    private Integer metaAffix;

    private String metaFrameSrc;

    /**
     * 
     */
    private String metaIcon;

    private Integer orderNo;

    private int isDelete = 0;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Menus other = (Menus) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMenuId() == null ? other.getMenuId() == null : this.getMenuId().equals(other.getMenuId()))
            && (this.getParentMenu() == null ? other.getParentMenu() == null : this.getParentMenu().equals(other.getParentMenu()))
            && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getComponent() == null ? other.getComponent() == null : this.getComponent().equals(other.getComponent()))
            && (this.getRedirect() == null ? other.getRedirect() == null : this.getRedirect().equals(other.getRedirect()))
            && (this.getMetaTitle() == null ? other.getMetaTitle() == null : this.getMetaTitle().equals(other.getMetaTitle()))
            && (this.getMetaAffix() == null ? other.getMetaAffix() == null : this.getMetaAffix().equals(other.getMetaAffix()))
            && (this.getMetaIcon() == null ? other.getMetaIcon() == null : this.getMetaIcon().equals(other.getMetaIcon()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMenuId() == null) ? 0 : getMenuId().hashCode());
        result = prime * result + ((getParentMenu() == null) ? 0 : getParentMenu().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getComponent() == null) ? 0 : getComponent().hashCode());
        result = prime * result + ((getRedirect() == null) ? 0 : getRedirect().hashCode());
        result = prime * result + ((getMetaTitle() == null) ? 0 : getMetaTitle().hashCode());
        result = prime * result + ((getMetaAffix() == null) ? 0 : getMetaAffix().hashCode());
        result = prime * result + ((getMetaIcon() == null) ? 0 : getMetaIcon().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", menuId=").append(menuId);
        sb.append(", parentMenu=").append(parentMenu);
        sb.append(", path=").append(path);
        sb.append(", name=").append(name);
        sb.append(", component=").append(component);
        sb.append(", redirect=").append(redirect);
        sb.append(", metaTitle=").append(metaTitle);
        sb.append(", metaAffix=").append(metaAffix);
        sb.append(", metaIcon=").append(metaIcon);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}