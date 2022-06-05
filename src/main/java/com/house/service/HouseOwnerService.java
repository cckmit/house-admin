package com.house.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.model.HouseOwner;

import java.util.Map;

/**
 *
 */
public interface HouseOwnerService extends IService<HouseOwner> {

    HouseOwner addOrUpdate(HouseOwner houseOwner);

    IPage<HouseOwner> getOwnerList(Map<String, Object> params, HouseOwner ho);

    /**
     * 获取当月新增数量
     */
    int getMaxNum();

    boolean removeOwnerById(String ownerId);
}
