package com.house.model.vo;

import com.house.common.bean.BaseVO;
import com.house.model.Images;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
public class LeaseVO extends BaseVO {

    @Schema(name = "租约ID")
    private String leaseId;

    private String unitType;

    private String houseArea;

    private String ownerLeaseId;

    private String createName;

    private String updateName;

    private String name;

    private String houseRent;


    private String leaseUrl;

    @Schema(name = "租约开始时间")
    private Long leaseStart;

    @Schema(name = "租约结束时间")
    private Long leaseEnd;

    private String rentCost;

    private String tenantId;

    private String interval;

    private String leaseType;

    private String houseDetailId;

    private String deposit;

    private List<String> imgList;

    private List<Images> imagesList;

    private Integer leaseStatus;
}
