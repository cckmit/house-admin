package com.house.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.house.model.BaseBean;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RoomRentDTO extends BaseBean {

    private Long roomRentId;
    // 房屋ID
    private String houseId;

    // 收租日期
    private Long collectRentDate;

    // 租金
    private Integer rent;

    // 是否交租 1 交租 0 未交
    private Integer isRev;

    // 交租还是付租 1 交 0 收
    private Integer isOut;

    // 小区名字
    @TableField(value = "district")
    private String district;

    // 楼号
    private Integer building;

    // 单元
    private Integer unit;

    // 房间号
    private  Integer number;
}
