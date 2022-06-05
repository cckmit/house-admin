package com.house.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Schema(name = "房源")
@NoArgsConstructor
@AllArgsConstructor
public class HouseDetailVO {

    private String houseDetailId;

    private String houseId;

    private String houseDetailName;

    private String tenantLeaseId;

    @Schema(name = "房源的房产名称")
    private String houseName;

    @Schema(name = "房源状态")
    private String hdStatus;

    @Schema(name = "租金")
    private Integer rentCost;

    @Schema(name = "租客")
    private String tenantName;

    @Schema(name = "合同编号")
    private String leaseId;

    @Schema(name = "新增或更新标识")
    private String type;

}
