package com.house.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@TableName("room_rent")
@Data
@NoArgsConstructor
public class RoomRent extends BaseBean{

    @TableId(value = "room_rent_id", type = IdType.INPUT)
    private Long roomRentId;
    // 房屋ID
    private String houseId;

    // 房东姓名
    private String landlordName;

    private String landlordPhone;

    // 收租日期
    private Long collectRentDate;

    // 租金
    private Integer rent;

    // 是否交租 1 交租 0 未交
    private Integer isRev;

    // 交租还是付租 1 交 0 收
    private Integer isOut;

}
