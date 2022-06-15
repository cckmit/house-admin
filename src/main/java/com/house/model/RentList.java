package com.house.model;

import com.baomidou.mybatisplus.annotation.*;
import com.house.model.base.SassBaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Schema(name = "租单列表")
@Data
@TableName(value = "rent_list")
@EqualsAndHashCode(callSuper = true)
public class RentList extends SassBaseModel {

    @TableId(value = "rent_list_id", type = IdType.AUTO)
    @Schema(name = "自增ID")
    private Long rentListId;

    @Schema(name = "小区")
    private String community;

    @Schema(name = "房号")
    private String roomNo;

    @Schema(name = "下次交租日期")
    private LocalDate nextDate;

    @Schema(name = "租金,小数点有两位")
    private Integer rent;

    @Schema(name = "起租日期")
    private LocalDate startDate;

    @Schema(name = "到期时间")
    private LocalDate endDate;

    @Schema(name = "手机号")
    private String phoneNo;

    @Schema(name = "关联名称")
    private String unionName;

    @Schema(name = "房型")
    private String roomType;

    @Schema(name = "水户号")
    private String waterNo;

    @Schema(name = "楼号")
    private String building;

    @Schema(name = "电户号")
    private String electricNo;

    @Schema(name = "燃气户号")
    private String gasNo;

    @Schema(name = "租金类型，0-未知，1-房东，2-租客")
    private Integer rentType;

    @Schema(name = "绑定用户ID")
    private String userId;

    @Schema(name = "图片地址，逗号，分割")
    private String imgText;

    @Schema(name = "收租周期")
    private Integer rentCycle;

    @Schema(name = "月租金")
    private Integer rentMonth;
}