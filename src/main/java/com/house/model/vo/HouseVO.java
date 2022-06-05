package com.house.model.vo;

import com.house.common.bean.BaseVO;
import com.house.model.House;
import com.house.model.HouseDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
public class HouseVO extends BaseVO {

    @Schema(name = "房屋ID")
    private String houseId;

    private Long index;

    @Schema(name = "楼号")
    private Integer building;

    @Schema(name = "小区id")
    private Integer communityId;

    @Schema(name = "门牌号")
    private Integer number;

    @Schema(name = "燃气户号")
    private String coalExpense;

    @Schema(name = "电费户号")
    private String electricalBill;

    @Schema(name = "水费户号")
    private String waterRate;

    @Schema(name = "房屋名称")
    private String houseName;

    @Schema(name = "到期时间")
    private Long rentEndTime;

    @Schema(name = "下次交租时间")
    private Long rentNextTime;

    @Schema(name = "起租时间")
    private Long rentStartTime;

    private Integer houseStatus;

    private String houseStatusName;

    @Schema(name = "房屋地址")
    private String address;

    @Schema(name = "房屋类型")
    private String[] houseType;


    private String unitType;

    @Schema(name = "业主ID")
    private String houseOwnerId;

    @Schema(name = "业主姓名")
    private String houseOwnerName;

    @Schema(name = "业主手机号")
    private String houseOwnerPhone;

    @Schema(name = "身份证信息")
    private String houseOwnerCard;

    @Schema(name = "房屋面积")
    private Double houseArea;

    @Schema(name = "房东租约ID")
    private String ownerLeaseId;

    private List<HouseDetail> houseDetailList;

    public House toHouse() {
        House house = House.builder()
                .houseId(houseId)
                .communityId(communityId)
                .building(building)
                .number(number)

                .houseName(houseName)
                .houseOwnerId(houseOwnerId)
                .houseArea(houseArea)
                .address(address)
                .build();

        return house;
    }
}
