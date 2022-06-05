package com.house.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(name = "房产明细")
@EqualsAndHashCode(callSuper = false)
@Builder
@TableName(value ="house_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseDetail extends BaseBean implements Serializable {

    @Schema(name = "房产明细ID")
    @TableId(type = IdType.INPUT)
    private String houseDetailId;

    @Schema(name = "房产ID")
    private String houseId;

    @Schema(name = "房间名称")
    private String houseDetailName;

    @Schema(name = "租客ID")
    private String tenantId;

    @Schema(name = "租客租约")
    private String tenantLeaseId;

    @Schema(name = "出租状态")
    private String hdStatus;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    @Schema(name = "房产明细ID前缀")
    public static final String PREFIX_HOUSE_DETAIL = "HD";
}