package com.house.service;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.common.Constant;
import com.house.exception.CheckException;
import com.house.mapper.RentListMapper;
import com.house.model.RentList;
import com.house.model.UserInfo;
import com.house.model.vo.RentListVO;
import com.house.utils.UserContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class RentListService extends ServiceImpl<RentListMapper, RentList> {

    private DictCommonService dictCommonService;

    @Autowired
    public void setDictCommonService(DictCommonService dictCommonService) {
        this.dictCommonService = dictCommonService;
    }

    public Boolean addOrUpdateRentInfo(RentListVO rentListVO) {
        String userId = UserContextHolder.getUserId();
        RentList rentList = new RentList();
        BeanUtils.copyProperties(rentListVO, rentList);
        boolean isOwner = rentListVO.getRentType() == Constant.PERSON_TYPE_LANDLARD;
        if (CharSequenceUtil.isNotBlank(rentList.getUnionName())
                && rentList.getUnionName().length() > 80) {
            throw new CheckException((isOwner ? "业主" : "租客") + "的姓名字符过长,请检查!");
        }

        // 判断小区存在与否
        LambdaQueryWrapper<RentList> dbRentInfo = Wrappers.<RentList>lambdaQuery()
                .eq(RentList::getCommunity, rentList.getCommunity())
                .eq(RentList::getRentType, rentList.getRentType())
                .eq(RentList::getBuilding, rentList.getBuilding())
                .eq(RentList::getRoomNo, rentList.getRoomNo())
                .eq(RentList::getUserId, userId)
                .ne(ObjectUtil.isNotEmpty(rentList.getRentListId()), RentList::getRentListId, rentList.getRentListId());
        RentList dbRentInfoRes = this.getOne(dbRentInfo);
        if (null != dbRentInfoRes) {
            throw new CheckException("房源已存在,请检查!");
        }

        rentList.setUserId(userId);
        String imgStr = String.join(",", rentListVO.getUploaderList());
        rentList.setImgText(imgStr);
        log.info("新增或更新租单信息参数:{}", rentList);

        dictCommonService.addCommunity(rentList.getCommunity());

        if (ObjectUtil.isNotEmpty(rentListVO.getRentListId())
                && ObjectUtil.notEqual(rentListVO.getRentListId(), 0)) {
            return this.updateById(rentList);
        } else {
            return this.save(rentList);
        }
    }

    public IPage<RentListVO> getRentList(RentListVO vo) {
        String userId = UserContextHolder.getUserId();
        QueryWrapper<RentList> queryWrapper = new QueryWrapper<RentList>()
                .eq("is_delete", Constant.NO_DELETE)
                .eq("user_id", userId)
                .orderByAsc("next_date");
        if (CharSequenceUtil.isNotBlank(vo.getRoomNoStr())) {
            String roomStr = vo.getRoomNoStr().replace("-", "");
            if (NumberUtil.isNumber(roomStr)) {
                queryWrapper.eq("concat(building,room_no)", roomStr);
            } else {
                queryWrapper.like("community", roomStr);
            }
        }
        queryWrapper.eq(Objects.nonNull(vo.getRentType()), "rent_type", vo.getRentType());
        IPage<RentList> page = new Page<>(vo.getPageNo(), vo.getPageSize());
        page = baseMapper.selectPage(page, queryWrapper);
        IPage<RentListVO> pageRes = new Page<>();
        if (ObjectUtil.isNotEmpty(page.getRecords())) {
            BeanUtils.copyProperties(page, pageRes);
            List<RentListVO> voList = page.getRecords().stream()
                    .map(source -> {
                        RentListVO target = new RentListVO();
                        BeanUtils.copyProperties(source, target);
                        if (CharSequenceUtil.isNotBlank(target.getImgText())) {
                            List<String> imgList = Stream.of(target.getImgText().split(","))
                                    .collect(Collectors.toList());
                            target.setUploaderList(imgList);
                        }
                        LocalDate now = LocalDate.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
                        String nextDateStr = formatter.format(target.getNextDate());
                        String curMonStr = formatter.format(now);
                        target.setBtnType(curMonStr.equals(nextDateStr) ? "warn" : "primary");
                        if (Integer.parseInt(curMonStr) > Integer.parseInt(nextDateStr)) {
                            target.setBtnType("warn");
                        }
                        return target;
                    })
                    .collect(Collectors.toList());
            pageRes.setRecords(voList);
        }
        return pageRes;
    }

    public RentListVO getByIdForRentList(RentListVO vo) {

        RentList rentList = getById(vo.getRentListId());
        BeanUtils.copyProperties(rentList, vo);
        if (CharSequenceUtil.isNotBlank(rentList.getImgText())) {
            List<String> imgList = Stream.of(rentList.getImgText().split(","))
                    .collect(Collectors.toList());

            vo.setUploaderList(imgList);
        }


        return vo;
    }


    public Map<String, Long> getCurrentMonthMoney() {
        Map<String, Long> resultMap = new HashMap<>(4);
        UserInfo userInfo = UserContextHolder.getUserInfo();
        Long expense = Optional.ofNullable(baseMapper.getCurrentMonthMoney(1, userInfo.getUserId()))
                .orElse(0L);
        Long income = Optional.ofNullable(baseMapper.getCurrentMonthMoney(2, userInfo.getUserId()))
                .orElse(0L);
        resultMap.put("expense", expense);
        resultMap.put("income", income);
        resultMap.put("profit", income - expense);
        return resultMap;
    }

    public Boolean removeRentInfoById(Integer id) {
        return this.removeById(id);
    }
}
