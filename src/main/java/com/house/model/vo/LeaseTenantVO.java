package com.house.model.vo;

import com.house.common.bean.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
public class LeaseTenantVO extends BaseVO {

    /**
     * 租约编号
     */
    private String leaseId;

    private String leaseUrl;

    private Long leaseStart;

    private Long leaseEnd;

    private Integer rentCost;

    private String ownerId;

    private String tenantId;

    private Integer interval;

    @Schema(name = "租约状态")
    private Integer leaseStatus;

    private String houseId;

    private String leaseType;

    private String houseOwnerName;

    private String houseName;

    private String houseDetailName;

    private String houseDetailId;

    @Schema(name = "租客姓名")
    private String tenantName;

    @Schema(name = "租客手机号")
    private String tenantPhone;

}
