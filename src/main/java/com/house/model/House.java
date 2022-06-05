package com.house.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.house.common.enums.StatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@TableName("house")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class House extends BaseBean {

    /**
     * 房源ID
     */
    @TableId(value = "house_id", type = IdType.INPUT)
    private String houseId;

    /**
     * 房源名称
     */
    private String houseName;

    /**
     * 房源地址
     */
    private String address;

    /**
     * 房源类型
     */
    private String houseType;

    /**
     * 房东ID
     */
    private String houseOwnerId;

    /**
     * 房屋面积
     */
    private Double houseArea;

    /**
     * 房东租约ID
     */
    private String ownerLeaseId;

    /**
     * @see StatusEnum
     */
    private Integer houseStatus;

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

    @Schema(name = "到期时间")
    private Long rentEndTime;

    @Schema(name = "下次交租时间")
    private Long rentNextTime;

    @Schema(name = "起租时间")
    private Long rentStartTime;

    public final static String PREFIX_HOUSE = "HO";
}
