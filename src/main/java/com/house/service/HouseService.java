package com.house.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.model.House;
import com.house.model.dto.RoomRentDTO;
import com.house.model.vo.HouseVO;

import java.util.Map;

public interface HouseService extends IService<House> {

    /**
     * 新增房产
     */
    boolean addHouseVO(HouseVO houseVO);

    boolean updHouse(HouseVO houseVO);

    boolean delHouse(String id);

    IPage<RoomRentDTO> findRoomRentList(RoomRentDTO roomRent);

    HouseVO getHouseVOById(String id);

    IPage<HouseVO> getHouseList(Map<String, Object> params, HouseVO houseVO);

    boolean addHouseV2(Map<String, Object> houseMap);
}
