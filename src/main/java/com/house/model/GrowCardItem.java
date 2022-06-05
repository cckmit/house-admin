package com.house.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GrowCardItem {
    private String titleMax;
    private String titleMin;
    private String icon;
    private Integer value;
    private Integer total;
    private String color;
    private String action;
}
