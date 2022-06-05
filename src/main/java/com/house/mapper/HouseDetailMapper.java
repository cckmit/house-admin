package com.house.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.house.model.HouseDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.house.model.vo.HouseDetailVO;
import com.house.model.vo.RoomInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;




public interface HouseDetailMapper extends BaseMapper<HouseDetail> {

    IPage<HouseDetailVO> getHouseDetailList(IPage<HouseDetail> page, @Param(Constants.WRAPPER) Wrapper<HouseDetail> wrapper);

    int getHouseDetailMaxNum(@Param("fristTime") Long fristTime);

    RoomInfo getRoomInfo(@Param("id") String id);

    /**
     * 获取总房源数量
     */
    @Select("select count(*) from house_detail where is_delete = 0 and user_id = #{userId}")
    Integer getHouseDetailCountByUserId(@Param("userId") String userId);
    /**
     * 获取已出租房源数量
     */
    @Select("select count(*) from house_detail where is_delete = 0 and user_id = #{userId} and hd_status = '已出租'")
    Integer getHouseDetailOffByUserId(@Param("userId") String userId);
}




