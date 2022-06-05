package com.house.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetaVO {

    private String icon;

    private String title;

    private Boolean affix;

    private Boolean ignorekeepalive;

    private String frameSrc;
}
