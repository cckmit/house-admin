package com.house.model.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SassBaseModel extends BaseModel {

    // 用户id,这里需要标记为填充字段
    @TableField(value = "user_id", fill = FieldFill.INSERT)
    private String userId;
}
