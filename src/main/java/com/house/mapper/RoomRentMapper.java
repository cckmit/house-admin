package com.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.model.RoomRent;
import com.house.model.dto.RoomRentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;



public interface RoomRentMapper extends BaseMapper<RoomRent> {

    IPage<RoomRentDTO> pageUser(IPage<RoomRentDTO> page, RoomRentDTO rentDTO);

}
