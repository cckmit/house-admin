package com.house.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @TableName images
 */
@EqualsAndHashCode(callSuper = false)
@TableName(value ="images")
@Data
public class Images extends BaseBean implements Serializable {
    /**
     * 房源照片id
     */
    @TableId(type = IdType.AUTO)
    private Integer imagesId;

    /**
     * 外键ID，关联ID
     */
    private String foreignId;
    private String imageName;

    /**
     * 照片url
     */
    private String ossUrl;

    /**
     * 
     */
    private String imagesType;

    @TableField(exist = false)
    private String uid;

    @TableField(exist = false)
    private String name;

    @TableField(exist = false)
    private String status;

    @TableField(exist = false)
    private String response;

    @TableField(exist = false)
    private String url;

    @TableField(exist = false)
    private String thumbUrl;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}