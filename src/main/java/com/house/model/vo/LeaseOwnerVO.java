package com.house.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Schema(name = "房东租约")
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
public class LeaseOwnerVO extends LeaseVO {

    @Schema(name = "房屋ID")
    private String houseId;

    @Schema(name = "房东名字")
    private String houseOwnerName;

    private String ownerId;

    private String houseName;

    private String address;

    private String houseType;

    private String houseOwnerId;


}
