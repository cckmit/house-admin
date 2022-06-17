package com.house.service;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.common.enums.StatusEnum;
import com.house.mapper.DictCommonMapper;
import com.house.model.DictCommon;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DictCommonService extends ServiceImpl<DictCommonMapper, DictCommon> {


    /**
     * 查询数据字典
     *
     * @param code  分类编码
     * @param value 分类名称
     */
    public List<DictCommon> dictCommonList(int code, String value) {

        LambdaQueryWrapper<DictCommon> lqw = Wrappers.<DictCommon>lambdaQuery()
                .eq(DictCommon::getCategoryCode, code)
                .like(StringUtils.isNotBlank(value), DictCommon::getDictDesc, value)
                .last("limit 5");

        return baseMapper.selectList(lqw);
    }

    /**
     * 新增维护小区
     */
    @Transactional
    public void addCommunity(String community) {
        if (CharSequenceUtil.isNotBlank(community)) {
            LambdaQueryWrapper<DictCommon> dictQuery = Wrappers.<DictCommon>lambdaQuery()
                    .eq(DictCommon::getDictDesc, community)
                    .eq(DictCommon::getCategoryCode, StatusEnum.COMMUNITY.getCode());
            DictCommon dictCommon = this.getOne(dictQuery);
            if (null == dictCommon) {
                DictCommon dictParam = new DictCommon()
                        .setDictDesc(community)
                        .setCategoryCode(StatusEnum.COMMUNITY.getCode());
                baseMapper.insert(dictParam);
            }
        }
    }

}

