package com.house.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.house.model.House;
import com.house.model.vo.HouseVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;



public interface HouseMapper extends BaseMapper<House> {


    Integer getHouseIdMaxNum(@Param("startTime") Long startTime);

    IPage<HouseVO> getHouseList(IPage<HouseVO> page, @Param(Constants.WRAPPER) QueryWrapper<HouseVO> queryWrapper);
}
