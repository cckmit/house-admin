package com.house.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RentCostDashboadVO {

    private String rentCostId;

    private Integer rentCostType;

    private String rentCostTypeName;

    private Integer rentCostMonth;

    private String rentCostUserName;

    private String rentCostUserPhone;

}

