package com.house.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.model.Lease;
import com.house.model.vo.LeaseOwnerVO;
import com.house.model.vo.LeaseRequestVO;
import com.house.model.vo.LeaseTenantVO;
import com.house.model.vo.LeaseVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 *
 */
public interface LeaseService extends IService<Lease> {

    IPage<LeaseTenantVO> getTenantLeaseList(Map<String, Object> params, LeaseTenantVO vo);

    Boolean addLease(Map<String, String> leaseMap);

    IPage<LeaseOwnerVO>  getOwnerLeaseList(Map<String, Object> params, LeaseOwnerVO vo);

    Boolean updateLease(LeaseVO leaseVO);

    /**
     * 租客退租
     */
    Boolean reversoContextById(LeaseRequestVO leaseRequestVO);

    /**
     * 根据ID获取租约
     */
    Lease getLeaseById(String leaseId);

    Boolean rcByLeaseId(String leaseId);
}
