package com.house.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.house.model.Lease;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.house.model.vo.LeaseOwnerVO;
import com.house.model.vo.LeaseTenantVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



public interface LeaseMapper extends BaseMapper<Lease> {

   IPage<LeaseTenantVO> getTenantLeaseList(IPage<LeaseTenantVO> page, @Param(Constants.WRAPPER) Wrapper<LeaseTenantVO> wrapper);

    int getLeaseMaxRowId();

    IPage<LeaseOwnerVO> getOwnerLeaseList(IPage<LeaseOwnerVO> page, @Param(Constants.WRAPPER) QueryWrapper<LeaseOwnerVO> queryWrapper);
}




