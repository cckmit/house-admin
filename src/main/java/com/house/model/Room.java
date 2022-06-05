package com.house.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 房间信息，这里使用了一对一查询，所以要加JsonIgnoreProperties注解，是为了springmvc返回时忽略序列化
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("rooms")
@JsonIgnoreProperties(value = {"handler"})
public class Room extends BaseBean implements Serializable {

    @TableId("room_id")
    private String roomId;
    @TableField(value = "room_house_id")
    private String roomHouseId; //关联房子id

    private String roomNumber; //房间编号

    private String roomTenantId; //租房人id

    private Double roomPrice; //租金

    private Date rentalDate; //出租日期

    private Date rentCollectionDate; //收租日期

    @TableField(exist = false)
    private Tenant tenant;


}
