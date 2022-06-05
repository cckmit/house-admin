package com.house.mapper;

import org.apache.ibatis.annotations.Param;

public interface CardNoMapper {
    String getAddress(@Param("areaCode") String areaCode);

}
