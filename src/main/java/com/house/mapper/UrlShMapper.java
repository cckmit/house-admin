package com.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.house.model.UrlSh;
import org.apache.ibatis.annotations.Param;

public interface UrlShMapper extends BaseMapper<UrlSh> {
    /**
     * 新增或更新
     */
    int saveOrUpdate(@Param("urlData") UrlSh urlData);
}