package com.house.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 租金列表
 *
 * @TableName rent_cost
 */
@EqualsAndHashCode(callSuper = false)
@TableName(value = "rent_cost")
@Data
public class RentCost extends BaseBean implements Serializable {
    /**
     * 租金ID
     */
    @TableId(type = IdType.INPUT)
    private String rentCostId;

    @TableField(exist = false)
    public final static String PREFIX_RENT = "RE";

    /**
     * 到期日期
     */
    private Long rentCostDate;

    /**
     * 1-房东 2-租客
     */
    private Integer rentCostType;

    /**·
     * 租金
     */
    private Integer rentCostMonth;

    /**
     * 缴纳状态
     */
    private Integer rentCostStatus;

    /**
     * 租金ID
     */
    private String leaseId;

    private Long realTime;

}