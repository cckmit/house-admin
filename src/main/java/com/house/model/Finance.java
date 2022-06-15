package com.house.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

import com.house.model.base.SassBaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 流水账
 */
@TableName(value ="finance")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Finance extends SassBaseModel implements Serializable {
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long financeId;

    /**
     * 房源地址拼接
     */
    private String address;

    /**
     * 租金类型，0-未知，1-房东，2-租客
     */
    private Integer rentType;

    /**
     * 交租时间
     */
    private LocalDate incomeDate;

    /**
     * 租单ID
     */
    private Long rentListId;

    /**
     * 租金
     */
    private Integer rentMoney;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}