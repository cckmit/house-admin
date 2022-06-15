package com.house.model.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseModel {

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(value = "create_name", fill = FieldFill.INSERT)
    private String createName;

    @TableField(value = "update_name", fill = FieldFill.INSERT_UPDATE)
    private String updateName;

    @TableLogic
    private int isDelete = 0;

}
