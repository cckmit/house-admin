package com.house.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "租金")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentOwnerVO {

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

    @Schema(name = "实际缴租日期")
    private Long realTime;

    private String leaseId;

}
