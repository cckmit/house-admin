package com.house.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TenantRemind {

    private String userId;

    private String houseDetailName;

    private String houseOwnerId;

    private String tenantName;

    private String rentCostMonth;

    private String tenantPhone;

    private Long rentCostDate;

    private String leaseId;

    private String ownerId;

}
