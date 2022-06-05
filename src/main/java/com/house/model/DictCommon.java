package com.house.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * 通用数据字典
 */
@Schema(name = "通用数据字典")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DictCommon extends BaseBean {
    /**
     * 数据字典主键
     */
    @Schema(name = "数据字典主键")
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