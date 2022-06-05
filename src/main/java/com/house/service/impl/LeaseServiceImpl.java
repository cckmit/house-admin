package com.house.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.common.Constant;
import com.house.common.enums.StatusEnum;
import com.house.exception.CheckException;
import com.house.mapper.LeaseMapper;
import com.house.mapper.RentCostMapper;
import com.house.mapper.TenantMapper;
import com.house.model.*;
import com.house.model.vo.LeaseRequestVO;
import com.house.utils.UUIDGenerator;
import com.house.utils.UserContextHolder;
import com.house.model.vo.LeaseOwnerVO;
import com.house.model.vo.LeaseTenantVO;
import com.house.model.vo.LeaseVO;
import com.house.utils.Query;
import com.house.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 租约服务
 */
@Slf4j
@Service
@Transactional
public class LeaseServiceImpl extends ServiceImpl<LeaseMapper, Lease> implements LeaseService {

    @Autowired
    private TenantService tenantService;
    @Autowired
    private TenantMapper tenantMapper;
    @Autowired
    private RentCostService rentCostService;
    @Autowired
    private RentCostMapper rentCostMapper;
    private ImagesService imagesService;
    private HouseDetailService houseDetailService;
    @Autowired
    private HouseService houseService;

    @Autowired
    public void setHouseDetailService(HouseDetailService houseDetailService) {
        this.houseDetailService = houseDetailService;
    }
    @Autowired
    public void setImagesService(ImagesService imagesService) {
        this.imagesService = imagesService;
    }

    @Override
    public IPage<LeaseTenantVO> getTenantLeaseList(Map<String, Object> params, LeaseTenantVO vo) {

        QueryWrapper<LeaseTenantVO> queryWrapper = new QueryWrapper<LeaseTenantVO>()
                .eq("le.is_delete", Constant.NO_DELETE)
                .eq("le.user_id", UserContextHolder.getUserId())
                .eq("le.lease_type", Constant.PERSON_TYPE_TENANT)
                .eq("le.lease_status", vo.getLeaseStatus())
                .orderByDesc("le.create_time");
        IPage<LeaseTenantVO> leaseTenantVOIPage = baseMapper.getTenantLeaseList(new Query<LeaseTenantVO>().getPage(params), queryWrapper);

        List<LeaseTenantVO> leaseTenantVOList = leaseTenantVOIPage.getRecords();

        return leaseTenantVOIPage;
    }

    @Override
    public IPage<LeaseOwnerVO> getOwnerLeaseList(Map<String, Object> params, LeaseOwnerVO vo) {

        QueryWrapper<LeaseOwnerVO> queryWrapper = new QueryWrapper<LeaseOwnerVO>()
                .eq("l.is_delete", Constant.NO_DELETE)
                .eq("l.user_id", UserContextHolder.getUserId())
                .eq("l.lease_status", vo.getLeaseStatus())
                .like(org.apache.commons.lang3.StringUtils.isNotBlank(vo.getHouseOwnerName()),"ho.house_owner_name",vo.getHouseOwnerName())
                .orderByDesc("l.create_time");

        IPage<LeaseOwnerVO> leaseOwnerVOIPage = baseMapper.getOwnerLeaseList(new Query<LeaseOwnerVO>().getPage(params), queryWrapper);

        List<LeaseOwnerVO> leaseOwnerVOList = leaseOwnerVOIPage.getRecords();

        return leaseOwnerVOIPage;
    }

    @Override
    public Boolean updateLease(LeaseVO leaseVO) {
        // 清除旧数据
        LambdaQueryWrapper<Images> imagesLqw = Wrappers.<Images>lambdaQuery()
                .eq(Images::getForeignId, leaseVO.getLeaseId())
                .eq(Images::getImagesType, "租约");
        imagesService.remove(imagesLqw);
        //  增加新数据
        return imagesService.addImages(leaseVO.getImgList(), leaseVO.getLeaseId(), "租约");
    }

    @Override
    public Boolean reversoContextById(LeaseRequestVO params) {
        // 查询租约
        Lease lease = this.getById(params.getLeaseId());
        if (ObjectUtil.isEmpty(lease)) {
            throw new CheckException(String.format("租约编号:【%s】不存在，请联系管理员～", params.getLeaseId()));
        }
        if (lease.getLeaseStatus() == 1) {
            throw new CheckException(String.format("租约编号:【%s】已解约，请刷新～", params.getLeaseId()));
        }
        if (lease.getLeaseType() == Constant.PERSON_TYPE_LANDLARD) {
            // 房东租约 - 先检测一下房产明细是否全部退租
            // 获取放权房源明细
            LambdaQueryWrapper<HouseDetail> houseDetailLqw = Wrappers.<HouseDetail>lambdaQuery()
                    .eq(HouseDetail::getHouseId, lease.getHouseDetailId())
                    .eq(HouseDetail::getUserId, UserContextHolder.getUserId())
                    .eq(HouseDetail::getIsDelete, Constant.NO_DELETE);
            List<HouseDetail> detailList = houseDetailService.list(houseDetailLqw);
            Set<String> houseDetailIdSet = detailList.stream()
                    .map(HouseDetail::getHouseDetailId).collect(Collectors.toSet());
            // 根据房产明细获取租约
            LambdaQueryWrapper<Lease> leaseLqw = Wrappers.<Lease>lambdaQuery()
                    .eq(Lease::getLeaseType, Constant.PERSON_TYPE_TENANT)
                    .eq(Lease::getLeaseStatus, StatusEnum.LEASE_STATUS_ON.getCode())
                    .in(Lease::getHouseDetailId,houseDetailIdSet)
                    .eq(Lease::getUserId, UserContextHolder.getUserId())
                    .eq(Lease::getIsDelete, Constant.NO_DELETE);
            List<Lease> leaseList = this.list(leaseLqw);
            if(ObjectUtil.isNotEmpty(leaseList)){
             throw new CheckException(String.format("房东租约解约需要先解除房产明细下各房源的租约，需要解约的租约编号列表:%s",leaseList.toString()));
            }
            House house = houseService.getById(lease.getHouseDetailId());
            house.setHouseStatus(StatusEnum.HOUSE_STATUS_OFF.getCode());
            houseService.updateById(house);
        } else {
            // 更新房源状态
            HouseDetail houseDetail = houseDetailService.getById(lease.getHouseDetailId());
            houseDetail.setTenantId("");
            houseDetail.setTenantLeaseId("");
            houseDetail.setHdStatus("未出租");
            // 清除租客、租约
            houseDetailService.updateById(houseDetail);
        }
        lease.setLeaseStatus(StatusEnum.LEASE_STATUS_OFF.getCode());
        boolean updateLeaseById = this.updateById(lease);
        // 更新租金列表状态
        LambdaQueryWrapper<RentCost> rentCostLqw = Wrappers.<RentCost>lambdaQuery()
                .eq(RentCost::getLeaseId, lease.getLeaseId())
                .eq(RentCost::getRentCostStatus,StatusEnum.RENT_COST_STATUS_NO.getCode());
        List<RentCost> rentCostList = rentCostService.list(rentCostLqw);
        rentCostList.forEach(item -> item.setRentCostStatus(StatusEnum.RENT_COST_STATUS_CANCEL.getCode()));
        boolean updateRent = rentCostService.updateBatchById(rentCostList);
        return updateLeaseById && updateRent;
    }

    private List<String> updateLeaseStatus(String houseDetailId) {
        LambdaQueryWrapper<Lease> leaseLqw = Wrappers.<Lease>lambdaQuery()
                .eq(Lease::getHouseDetailId, houseDetailId)
                .eq(Lease::getUserId, UserContextHolder.getUserId())
                .eq(Lease::getLeaseType, Constant.PERSON_TYPE_TENANT)
                .eq(Lease::getIsDelete, Constant.NO_DELETE)
                .eq(Lease::getLeaseStatus, StatusEnum.LEASE_STATUS_ON.getCode());

        List<Lease> leaseList = this.list(leaseLqw);
        if (ObjectUtil.isEmpty(leaseList)) {
            throw new CheckException("未查询到关联的租约，请联系管理员～");
        }
        log.info("需要解除租约的houseDetailId:{},租约列表:{}", houseDetailId, leaseList);
        List<String> leaseIds = new ArrayList<>();
        leaseList.forEach(lease -> {
            // 校验租约状态 其实不用校验 重复解约结果一样
            lease.setLeaseStatus(StatusEnum.LEASE_STATUS_OFF.getCode());
            this.updateById(lease);
            leaseIds.add(lease.getLeaseId());
        });
        return leaseIds;
    }

    @Override
    public Lease getLeaseById(String leaseId) {
        LambdaQueryWrapper<Lease> lambdaQueryWrapper = Wrappers.<Lease>lambdaQuery()
                .eq(Lease::getUserId, UserContextHolder.getUserId())
                .eq(Lease::getLeaseId, leaseId);

        return Optional.ofNullable(baseMapper.selectOne(lambdaQueryWrapper))
                .orElseThrow(() -> new CheckException(String.format("编号为:%s的租约不存在!", leaseId)));
    }

    @Override
    public Boolean rcByLeaseId(String leaseId) {
        // 查询房子是否已经出租出去
        Lease lease = this.getById(leaseId);
        if (ObjectUtil.isEmpty(lease)) {
            throw new CheckException("租约编号：%s不存在，请联系管理员～");
        }
        LambdaQueryWrapper<House> houseLqw = Wrappers.<House>lambdaQuery()
                .eq(House::getOwnerLeaseId, leaseId)
                .eq(House::getUserId, UserContextHolder.getUserId());
        House house = houseService.getOne(houseLqw);

        LambdaQueryWrapper<HouseDetail> houseDetailLqw = Wrappers.<HouseDetail>lambdaQuery()
                .eq(HouseDetail::getHouseId, house.getHouseId());
        List<HouseDetail> houseDetailList = houseDetailService.list(houseDetailLqw);

        boolean flag = false;
        StringBuilder errorInfo = new StringBuilder();
        for (HouseDetail houseDetail : houseDetailList) {
            if (StringUtils.isNotBlank(houseDetail.getTenantLeaseId())) {
                errorInfo.append("房产名下的房间:【")
                        .append(houseDetail.getHouseDetailName())
                        .append("】,存在租客租约编号:【")
                        .append(houseDetail.getTenantLeaseId())
                        .append("】，请先进行退租！");
                flag = true;
            }
        }
        if (flag) {
            throw new CheckException(errorInfo.toString());
        }

        // 查询要清除的租金列表
        boolean removeRent = this.removeRent(new ArrayList<String>() {{
            add(leaseId);
        }});

        // 更改租约状态
        lease.setLeaseStatus(StatusEnum.LEASE_STATUS_OFF.getCode());
        boolean updateLeaseStatus = this.updateById(lease);


        return removeRent && updateLeaseStatus;
    }

    @Override
    public Boolean addLease(Map<String, String> leaseMap) {
        checkAddLease(leaseMap, true);
        // 新增租客
        String tenantId = UUIDGenerator.getNextId(Tenant.TENANT_PREFIX, tenantMapper.getTenantMaxNum());
        String leaseId = UUIDGenerator.getNextId(Lease.PREFIX_LEASE, baseMapper.getLeaseMaxRowId());
        String houseDetailId = leaseMap.get("houseDetailId");
        Tenant tenant = new Tenant();
        tenant.setTenantId(tenantId)
                .setTenantPhone(leaseMap.get("tenantPhone"))
                .setSex(leaseMap.get("sex"))
                .setTenantName(leaseMap.get("tenantName"))
                .setLeaseId(leaseId)
                .setHouseDetailId(houseDetailId)
                .setTenantCard(leaseMap.get("tenantCard"))
                .setAddress(leaseMap.get("address"))
                .setBirthday(leaseMap.get("birthday"));
        if(leaseMap.containsKey("age") && StringUtils.isNotBlank(leaseMap.get("age"))){
            tenant.setAge(Integer.valueOf(leaseMap.get("age")));
        }
        boolean addTenRes = tenantService.save(tenant);
        // 新增租约
        Lease lease = new Lease();
        lease.setLeaseId(leaseId);
        if (leaseMap.containsKey(Lease.LEASE_START_FIELD)) {
            lease.setLeaseStart(Long.valueOf(leaseMap.get(Lease.LEASE_START_FIELD)));
        }
        lease.setLeaseEnd(Long.valueOf(leaseMap.get(Lease.LEASE_END_FIELD)));
        lease.setTenantId(tenantId);
        lease.setHouseDetailId(houseDetailId);
        lease.setRentCost(Integer.valueOf(leaseMap.get("rentCost")));
        lease.setDeposit(Integer.valueOf(leaseMap.get("deposit")));
        lease.setInterval(Integer.valueOf(leaseMap.get("interval")));
        lease.setLeaseYears(Integer.valueOf(leaseMap.get("leaseYears")));
        lease.setLeaseStatus(0);
        lease.setLeaseType(Constant.PERSON_TYPE_TENANT);
        this.save(lease);

        for (int i = 0; i < 100; i++) {
            String dateKey = "rentDateTime" + i;
            String costKey = "rentCost" + i;
            String statusKey = "giveMoney" + i;
            if (leaseMap.containsKey(dateKey)) {
                RentCost rentCost = new RentCost();
                rentCost.setRentCostId(UUIDGenerator.getNextId(RentCost.PREFIX_RENT, rentCostMapper.getRentCostMaxNum()));
                rentCost.setRentCostType(Constant.PERSON_TYPE_TENANT);
                rentCost.setRentCostDate(Long.valueOf(leaseMap.get(dateKey)));
//                缴纳状态 0-未交 1-已交
                rentCost.setRentCostStatus(Boolean.parseBoolean(leaseMap.get(statusKey)) ? 1 : 0);
                rentCost.setRentCostMonth(Integer.valueOf(leaseMap.get(costKey)));
                rentCost.setLeaseId(lease.getLeaseId());
                rentCostService.save(rentCost);
            } else {
                break;
            }
        }
        // 更新房源状态
        HouseDetail houseDetail = HouseDetail.builder()
                .houseDetailId(houseDetailId)
                .tenantLeaseId(leaseId)
                .tenantId(tenantId)
                .hdStatus("已出租")
                .build();
        boolean updateHouseDetail = houseDetailService.updateById(houseDetail);
        return addTenRes && updateHouseDetail;
    }

    public void checkAddLease(Map<String, String> leaseMap, boolean isTenant) {
        if (isTenant) {
            if (!leaseMap.containsKey("tenantPhone")) {
                throw new CheckException("租客手机号为必填！");
            }
        }
        if (!leaseMap.containsKey("interval")) {
            throw new CheckException("交租间隔必填！");
        }

        if (!leaseMap.containsKey(Lease.LEASE_END_FIELD) || ObjectUtil.isEmpty(leaseMap.get(Lease.LEASE_END_FIELD))) {
            throw new CheckException("到期时间不能为空！");
        }

        if (!leaseMap.containsKey("houseDetailId") || ObjectUtil.isEmpty(leaseMap.get("houseDetailId"))) {
            throw new CheckException("房源ID未找到，请联系管理员！");
        }
        HouseDetail houseDetail = houseDetailService.getById(leaseMap.get("houseDetailId"));
        if (Objects.isNull(houseDetail)) {
            throw new CheckException(String.format("房源ID【%s】的数据未查询到!", leaseMap.get("houseDetailId")));
        }
        if ("已出租".equals(houseDetail.getHdStatus())) {
            throw new CheckException(String.format("房源【%s】已出租，请勿重复操作!", houseDetail.getHouseDetailName()));
        }
    }

    private boolean removeRent(List<String> leaseIds) {
        // 删除租金列表
        LambdaQueryWrapper<RentCost> delRent = Wrappers.<RentCost>lambdaQuery()
                .eq(RentCost::getRentCostStatus, StatusEnum.RENT_COST_STATUS_NO)
                .in(RentCost::getLeaseId, leaseIds)
                .eq(RentCost::getUserId, UserContextHolder.getUserId());
        List<RentCost> rentCostList = rentCostService.list(delRent);
        List<String> rentCostIdS = rentCostList.stream()
                .map(RentCost::getRentCostId)
                .collect(Collectors.toList());
        return rentCostService.removeByIds(rentCostIdS);
    }
}