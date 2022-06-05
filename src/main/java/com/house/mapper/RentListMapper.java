package com.house.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.model.HouseOwner;
import com.house.model.RentList;
import com.house.model.vo.RentListVO;
import org.apache.ibatis.annotations.Param;

public interface RentListMapper extends BaseMapper<RentList> {
    IPage<RentList> getRentList(IPage<HouseOwner> page, QueryWrapper<RentListVO> queryWrapper);

    /**
     * 获取当月支出与收入
     */
    Long getCurrentMonthMoney(@Param("rentType") Integer rentType,
                              @Param("userId") String userId);

}