package com.house.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.house.model.OwnerRemind;
import com.house.model.TenantRemind;
import com.house.model.RentCost;
import com.house.model.vo.RentCostDashboadVO;
import com.house.model.vo.RentOwnerVO;
import com.house.model.vo.RentTenantVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RentCostMapper extends BaseMapper<RentCost> {

    IPage<RentOwnerVO> getOwnerRentList(IPage<RentOwnerVO> page, @Param(Constants.WRAPPER) QueryWrapper<RentOwnerVO> queryWrapper);

    IPage<RentTenantVO> getTenantRentList(IPage<RentTenantVO> page, @Param(Constants.WRAPPER) QueryWrapper<RentTenantVO> queryWrapper);

    int getRentCostMaxNum();

    List<OwnerRemind> getOwnerRemind(@Param("nowTime") Long nowTime);

    List<TenantRemind> getTenantRemind(@Param("nowTime") Long nowTime);

    List<RentCostDashboadVO> getRecentRentCost(@Param("userId") String userId);
}