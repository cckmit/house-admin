package com.house.model;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Schema(name = "rent_list")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "rent_list")
public class RentList {

    @TableId(value = "rent_list_id", type = IdType.AUTO)
    @Schema(name = "自增ID")
    private Integer rentListId;

    @Schema(name = "小区")
    private String community;

    @Schema(name = "房号")
    private String roomNo;

    @Schema(name = "期限")
    private String intervalDate;

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

    @TableLogic
    private int isDelete = 0;
}