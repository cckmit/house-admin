package com.house.model;

import lombok.Data;

@Data
public class OwnerRemind {

    private String userId;

    private String houseName;

    private String houseOwnerId;

    private String houseOwnerName;

    private String rentCostMonth;

    private String houseOwnerPhone;

    private Long rentCostDate;

    private String leaseId;

    private String ownerId;

}
