package com.house.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 租客信息
 */
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("tenant")
public class Tenant extends BaseBean{

    @TableId(type = IdType.INPUT)
    private String tenantId;

    private String tenantName;

    private String tenantPhone;

    private String tenantCard;

    private String sex;

    @TableField(exist = false)
    public final static String TENANT_PREFIX = "T";

    private Integer age;

    @Schema(name = "房源")
    private String houseDetailId;

    @Schema(name = "租约ID")
    private String leaseId;

    @Schema(name = "省")
    private String province;

    @Schema(name = "市")
    private String city;

    private String district;

    private String birthday;

    private String address;


}
