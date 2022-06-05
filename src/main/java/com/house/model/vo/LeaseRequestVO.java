package com.house.model.vo;

import com.house.common.Constant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LeaseRequestVO {
    private String leaseId;
    /**
     * @see Constant
     */
    private Integer leaseType;
}
