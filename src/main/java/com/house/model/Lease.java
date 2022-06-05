package com.house.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 *
 */
@EqualsAndHashCode(callSuper = false)
@TableName(value ="lease")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lease extends BaseBean implements Serializable {
    /**
     * 租约ID
     */
    @TableId(type = IdType.INPUT)
    private String leaseId;

    /**
     * 租约PDF
     */
    private Integer leaseUrl;
    private Integer leaseYears;
    /**
     * 间隔
     */
    @TableField("`interval`")
    private Integer interval;
    /**
     * 房东id
     */
    private String ownerId;
    /**
     * 租客id
     */
    private String tenantId;

    /**
     * 租约开始日期
     */
    private Long leaseStart;
    public static final String LEASE_START_FIELD ="rentStartTime";
    public static final String LEASE_END_FIELD ="rentEndTime";

    /**
     * 租约结束日期
     */
    private Long leaseEnd;

    @Schema(name = "租金")
    private Integer rentCost;

    @Schema(name = "押金")
    private Integer deposit;

    @Schema(name = "房屋明细ID")
    private String houseDetailId;

    @Schema(name = "租约类型")
    private Integer leaseType;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    public static final String PREFIX_LEASE = "LE";

    /**
     * 租约状态 0-租约正常 1-已解约
     */
    private Integer leaseStatus;

}