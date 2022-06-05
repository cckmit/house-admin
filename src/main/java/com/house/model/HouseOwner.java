package com.house.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @TableName house_owner
 */
@EqualsAndHashCode(callSuper = false)
@TableName(value = "house_owner")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class HouseOwner extends BaseBean implements Serializable {
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
    /**
     * 房东ID
     */
    @TableId(type = IdType.INPUT)
    private String houseOwnerId;
    /**
     * 房东名称
     */
    private String houseOwnerName;
    /**
     * 房东手机号
     */
    private String houseOwnerPhone;
    /**
     * 身份证
     */
    private String houseOwnerCard;

    private String deletedVersion;

    public HouseOwner(String houseOwnerName, String houseOwnerPhone) {
        this.houseOwnerName = houseOwnerName;
        this.houseOwnerPhone = houseOwnerPhone;
    }

    @TableField(exist = false)
    public static final String PREFIX= "HO";

}