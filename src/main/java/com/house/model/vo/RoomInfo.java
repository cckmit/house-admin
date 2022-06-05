package com.house.model.vo;

import com.house.model.RentCost;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(name = "房间详细信息")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomInfo {

    private String houseDetailId;

    private String houseName;

    private String houseOwnerName;

    private String houseOwnerPhone;

    private String ownerLeaseId;

    private String tenantName;

    private String tenantPhone;

    private Integer ownerRentCost;

    private Integer tenantRentCost;

    @Schema(name = "租期")
    private Integer leaseYears;

    private String tenantLeaseId;

    @Schema(name = "租金明细编号")
    private String rentCostId;

    private List<RentCost> costList;
}
