package com.house.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.house.model.DictCommon;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;



public interface DictCommonMapper extends BaseMapper<DictCommon> {
    int deleteByPrimaryKey(Integer dictId);

    int insertSelective(DictCommon record);

    DictCommon selectByPrimaryKey(Integer dictId);

    int updateByPrimaryKeySelective(DictCommon record);

    int updateByPrimaryKey(DictCommon record);
}