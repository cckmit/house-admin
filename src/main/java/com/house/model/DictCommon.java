package com.house.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.house.model.base.BaseModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 通用数据字典
 */
@Schema(name = "通用数据字典")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class DictCommon extends BaseModel {
    /**
     * 数据字典主键
     */
    @Schema(name = "数据字典主键")
    @TableId(value = "dict_id", type = IdType.AUTO)
    private Integer dictId;

    /**
     * 编码
     */
    @Schema(name = "编码")
    private String dictCode;

    /**
     * 名称
     */
    @Schema(name = "名称")
    private String dictDesc;

    /**
     * 分类编码
     */
    @Schema(name = "分类编码")
    private Integer categoryCode;

    /**
     * 分类说明
     */
    @Schema(name = "分类说明")
    private String categoryDesc;
}