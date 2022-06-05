package com.house.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.house.model.HouseOwner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 *
 */
public interface HouseOwnerMapper extends BaseMapper<HouseOwner> {

    Integer saveAndResId(@Param("houseOwner") HouseOwner houseOwner);

    /**
     * 获取房东列表
     */
    IPage<HouseOwner> getOwnerList(IPage<HouseOwner> page, @Param(Constants.WRAPPER) Wrapper<HouseOwner> wrapper);

    int getMaxNum(@Param("firstTime") Long firstTime);
}




