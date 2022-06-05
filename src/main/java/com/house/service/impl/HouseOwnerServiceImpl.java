package com.house.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.house.common.Constant;
import com.house.common.enums.ResultCode;
import com.house.exception.CheckException;
import com.house.mapper.HouseOwnerMapper;
import com.house.model.House;
import com.house.model.HouseOwner;
import com.house.model.Lease;
import com.house.service.HouseOwnerService;
import com.house.service.HouseService;
import com.house.service.LeaseService;
import com.house.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 房东管理模块
 */
@Slf4j
@Service
@Transactional
public class HouseOwnerServiceImpl extends ServiceImpl<HouseOwnerMapper, HouseOwner> implements HouseOwnerService {

    @Autowired
    private HouseService houseService;
    @Autowired
    private LeaseService leaseService;

    /**
     * 新增并返回ID
     */
    public HouseOwner addOrUpdate(HouseOwner houseOwner) {
        if (StringUtils.isNotBlank(houseOwner.getHouseOwnerCard())) {
            boolean checkCardNo = CardNoUtil.isIDNumber(houseOwner.getHouseOwnerCard());
            if (!checkCardNo) {
                throw new CheckException("身份证号校验失败！");
            }
        }

        boolean ipUpdate = StringUtils.isNotBlank(houseOwner.getHouseOwnerId());
        LambdaQueryWrapper<HouseOwner> lqw = Wrappers.<HouseOwner>lambdaQuery()
                .eq(HouseOwner::getHouseOwnerName, houseOwner.getHouseOwnerName())
                .eq(HouseOwner::getHouseOwnerPhone, houseOwner.getHouseOwnerPhone())
                .eq(HouseOwner::getUserId, UserContextHolder.getUserId())
                .eq(HouseOwner::getDeletedVersion, Constant.NO_DELETE)
                .ne(ipUpdate, HouseOwner::getHouseOwnerId, houseOwner.getHouseOwnerId());
        HouseOwner ownerRes = this.getOne(lqw);
        if (ObjectUtil.isNotEmpty(ownerRes)) {
            throw new CheckException(String.format("房东姓名【%s】,手机号【%s】已存在,编号为:【%s】", ownerRes.getHouseOwnerName(), ownerRes.getHouseOwnerPhone(), ownerRes.getHouseOwnerId()));
        }

        if (ipUpdate) {
            HouseOwner oldData = this.getById(houseOwner.getHouseOwnerId());
            if (!houseOwner.getHouseOwnerName().equals(oldData.getHouseOwnerName())) {
                LambdaQueryWrapper<Lease> leaseLambdaQueryWrapper = Wrappers.<Lease>lambdaQuery()
                        .eq(Lease::getOwnerId, houseOwner.getHouseOwnerId())
                        .eq(Lease::getUserId, UserContextHolder.getUserId())
                        .eq(Lease::getIsDelete, Constant.NO_DELETE);
                List<Lease> leaseList = leaseService.list(leaseLambdaQueryWrapper);
                if (ObjectUtil.isNotEmpty(leaseList)) {
                    throw new CheckException("房东租约已签订,无法修改房东姓名!");
                }
            }
            // 更新
            this.updateById(houseOwner);
            return ownerRes;
        } else {
            // 新增
            houseOwner.setHouseOwnerId(UUIDGenerator.getNextId(HouseOwner.PREFIX, this.getMaxNum()));
            this.save(houseOwner);
            return houseOwner;
        }
    }

    @Override
    public IPage<HouseOwner> getOwnerList(Map<String, Object> params, HouseOwner ho) {
        QueryWrapper<HouseOwner> queryWrapper = new QueryWrapper<HouseOwner>()
                .like(StringUtils.isNotBlank(ho.getHouseOwnerName()), "ho.house_owner_name", ho.getHouseOwnerName())
                .eq(StringUtils.isNotBlank(ho.getHouseOwnerPhone()), "ho.house_owner_phone", ho.getHouseOwnerPhone())
                .eq(StringUtils.isNotBlank(ho.getHouseOwnerCard()), "ho.house_owner_card", ho.getHouseOwnerCard())
                .eq("ho.is_delete", Constant.NO_DELETE)
                .eq("ho.user_id", UserContextHolder.getUserId());


        return baseMapper.getOwnerList(new Query<HouseOwner>().getPage(params), queryWrapper);
    }

    @Override
    public int getMaxNum() {
        return baseMapper.getMaxNum(DateUtils.getFristOfMonth());
    }

    @Override
    public boolean removeOwnerById(String ownerId) {
        if (StringUtils.isBlank(ownerId)) {
            throw new CheckException(ResultCode.PARAM_IS_INVALID);
        }
        LambdaQueryWrapper<House> houseLqw = Wrappers.<House>lambdaQuery()
                .eq(House::getHouseOwnerId, ownerId)
                .eq(House::getUserId, UserContextHolder.getUserId());
        List<House> houses = houseService.list(houseLqw);
        if (ObjectUtil.isNotEmpty(houses)) {
            String houseNameStr = houses.stream()
                    .map(House::getHouseName)
                    .collect(Collectors.joining());
            throw new CheckException("存在绑定该房东的房源:【" + houseNameStr + "】");
        }

        HouseOwner houseOwner = new HouseOwner();
        houseOwner.setIsDelete(Constant.DELETED);
        houseOwner.setDeletedVersion(ownerId);
        houseOwner.setHouseOwnerId(ownerId);
        this.removeById(ownerId);
        return SqlHelper.retBool(baseMapper.updateById(houseOwner));
    }

}




