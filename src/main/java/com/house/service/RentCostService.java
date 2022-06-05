package com.house.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.model.vo.RentVO;
import com.house.model.RentCost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.model.vo.RentOwnerVO;
import com.house.model.vo.RentTenantVO;
import com.taobao.api.ApiException;

import java.util.Map;

/**
 *
 */
public interface RentCostService extends IService<RentCost> {

    IPage<RentOwnerVO> getOwnerRentList(Map<String, Object> params, RentOwnerVO vo);

    IPage<RentTenantVO> getTenantRentList(Map<String, Object> params, RentTenantVO vo);

    Boolean addOrUpdateRent(RentVO vo);

    Boolean sendOwnerRentDingTalk() throws ApiException;

    Boolean sendTenantRentDingTalk() throws ApiException;
}
