package com.house.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RentVO {
    @Schema(name = "房源名称")
    private String houseName;

    @Schema(name = "收租日期")
    private Long rentCostDate;

    @Schema(name = "租金")
    private Integer rentCostMonth;

    @Schema(name = "状态")
    private Integer rentCostStatus;

    @Schema(name = "房东姓名")
    private String houseOwnerName;

    @Schema(name = "房东手机号")
    private String houseOwnerPhone;

    @Schema(name = "租金ID")
    private String rentCostId;

    @Schema(name = "相差多少天")
    private Long diffDay;

    private String diffDayInfo;

    @Schema(name = "房源名称")
    private String HouseDetailName;

    @Schema(name = "租金")
    private Integer rentCost;

    @Schema(name = "状态")
    private Integer rentCostCost;

    @Schema(name = "租客姓名")
    private String tenantName;

    @Schema(name = "租客手机号")
    private String tenantPhone;

    private String rentCostType;

    private String leaseId;

    private String createTime;

    private String updateTime;

    private String createName;

    private String updateName;

    private String leaseUrl;

    private Long leaseStart;

    private Long leaseEnd;

    private String ownerId;

    private String tenantId;

    private String interval;

    private String houseDetailId;

    private String deposit;

    private String leaseType;

    private String houseId;

    private String houseDetailName;

    private String tenantLeaseId;

    private String hdStatus;

    private String tenantCard;

    private String sex;

    private String age;

    private String province;

    private String city;

    @Schema(name = "实际收缴租日期")
    private Long realTime;

    @Schema(name = "新增/更新标识")
    private String type;
}
