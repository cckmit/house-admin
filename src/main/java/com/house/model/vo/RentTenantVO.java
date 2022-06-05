package com.house.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "租金")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentTenantVO {

    @Schema(name = "租金")
    private Integer rentCost;

    @Schema(name = "租客姓名")
    private String tenantName;

    @Schema(name = "租客手机号")
    private String tenantPhone;

    @Schema(name = "收租状态")
    private Integer rentCostStatus;

    @Schema(name = "收租日期")
    private Long rentCostDate;

    @Schema(name = "相差多少天")
    private Long diffDay;

    private String diffDayInfo;

    private String rentCostId;

    private String rentCostType;

    private String rentCostMonth;

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

    @Schema(name = "房源名称")
    private String houseDetailName;

    private String tenantLeaseId;

    private String hdStatus;

    private String tenantCard;

    private String sex;

    private String age;

    private String province;

    private String city;

    @Schema(name = "实际收租日期")
    private Long realTime;
}
