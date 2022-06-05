package com.house.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BaseBean {

    @TableLogic
    private int isDelete = 0;

    @TableField(exist = false)
    private int pageNo = 1;

    @TableField(exist = false)
    private int pageSize = 10;

    // 用户id,这里需要标记为填充字段
    @TableField(value = "user_id",fill = FieldFill.INSERT)
    private String userId;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;

    @TableField(value = "create_name",fill = FieldFill.INSERT)
    private String createName;

    @TableField(value = "update_name",fill = FieldFill.INSERT_UPDATE)
    private String updateName;

}
