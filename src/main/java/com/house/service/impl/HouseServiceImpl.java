package com.house.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.common.Constant;
import com.house.model.House;
import com.house.model.HouseDetail;
import com.house.model.HouseOwner;
import com.house.model.dto.RoomRentDTO;
import com.house.model.vo.HouseVO;
import com.house.utils.DateUtils;
import com.house.utils.Query;
import com.house.utils.UUIDGenerator;
import com.house.utils.UserContextHolder;
import com.house.mapper.*;
import com.house.service.HouseDetailService;
import com.house.service.HouseOwnerService;
import com.house.service.HouseService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

    @Autowired
    private RoomRentMapper roomRentMapper;
    private HouseOwnerService houseOwnerService;
    @Autowired
    private HouseDetailService houseDetailService;
    @Autowired
    private HouseDetailMapper houseDetailMapper;

    @Autowired
    public void setHouseOwnerService(HouseOwnerService houseOwnerService) {
        this.houseOwnerService = houseOwnerService;
    }

    @Override
    public IPage<HouseVO> getHouseList(Map<String, Object> params, HouseVO houseVO) {

        QueryWrapper<HouseVO> queryWrapper = new QueryWrapper<HouseVO>()
                .like(StringUtils.isNotBlank(houseVO.getHouseName()), "h.house_name", houseVO.getHouseName())
                .eq("h.user_id", UserContextHolder.getUserId())
                .eq("h.is_delete", Constant.NO_DELETE);

        IPage<HouseVO> houseVOIPage = baseMapper.getHouseList(new Query<HouseVO>().getPage(params), queryWrapper);

        List<HouseVO> houseVOList = houseVOIPage.getRecords();
        if (ObjectUtil.isNotEmpty(houseVOList)) {
            houseVOList.forEach(vo -> {
                String index = vo.getHouseId().replaceAll("HO", "");
                vo.setIndex(Long.valueOf(index));
                switch (vo.getHouseStatus()) {
                    case 1:
                        vo.setHouseStatusName("房屋正常");
                    case 2:
                        vo.setHouseStatusName("房屋已退租");
                    default:
                        vo.setHouseStatusName("房屋正常");
                }
            });
        }
        return houseVOIPage;
    }

    @Override
    public boolean addHouseV2(Map<String, Object> houseMap) {
        log.info("houseMap====》{}", JSON.toJSONString(houseMap));
        return false;
    }

    @Override
    public boolean updHouse(HouseVO houseVO) {
        House house = BeanUtil.toBean(houseVO, House.class);
        house.setUserId(UserContextHolder.getUserId());
        house.setHouseType(Stream.of(houseVO.getHouseType()).map(String::valueOf).collect(Collectors.joining(",")));
        house.setHouseName(houseVO.getHouseName());
        boolean updateHouse = this.updateById(house);

        // 更新房东
        HouseOwner houseOwner = new HouseOwner();
        houseOwner.setHouseOwnerId(houseVO.getHouseOwnerId());
        houseOwner.setHouseOwnerName(houseVO.getHouseOwnerName());
        houseOwner.setHouseOwnerPhone(houseVO.getHouseOwnerPhone());
        boolean updateOwner = houseOwnerService.updateById(houseOwner);

        return updateOwner && updateHouse;
    }

    @Override
    public boolean addHouseVO(HouseVO houseVO) {
        // check 业主
        this.checkYzExist(houseVO);
        new HouseVO();
        log.info("houseVO:{}", houseVO);
        // 新增房产
        String houseId = UUIDGenerator.getNextId(House.PREFIX_HOUSE, baseMapper.getHouseIdMaxNum(DateUtils.getFristOfMonth()));

        House house = houseVO.toHouse();

        this.save(house);

        HouseDetail houseDetail = HouseDetail.builder()
                .houseDetailId(UUIDGenerator.getNextId(HouseDetail.PREFIX_HOUSE_DETAIL
                        , houseDetailMapper.getHouseDetailMaxNum(DateUtils.getFristOfMonth())))
                .houseDetailName(house.getHouseName())
                .houseId(houseId)
                .build();
        houseDetailService.save(houseDetail);
//        // 新增租约
//        Lease lease = Lease.builder()
//                .leaseId(leaseId)
//                .leaseEnd(Long.valueOf(houseMap.get("rentEndTime").toString()))
//                .leaseStart(Long.valueOf(houseMap.get("rentStartTime").toString()))
//                .interval(Integer.valueOf(houseMap.get("interval").toString()))
//                .deposit(Integer.valueOf(houseMap.get("deposit").toString()))
//                .ownerId(houseOwnerResult.getHouseOwnerId())
//                .rentCost(Integer.valueOf(houseMap.get("rentCost").toString()))
//                .leaseYears(Integer.valueOf(houseMap.get("leaseYears").toString()))
//                .leaseType(Constant.PERSON_TYPE_LANDLARD)
//                .leaseStatus(StatusEnum.LEASE_STATUS_ON.getCode())
//                .build();
//        lease.setHouseDetailId(houseId);
//        boolean leaseRes = leaseService.save(lease);
//        // 新增租金列表
//        for (int i = 0; i < 100; i++) {
//            String dateKey = "rentDateTime" + i;
//            String costKey = "rentCost" + i;
//            String statusKey = "giveMoney" + i;
//            if (houseMap.containsKey(dateKey)) {
//                RentCost rentCost = new RentCost();
//                rentCost.setRentCostId(UUIDGenerator.getNextId(RentCost.PREFIX_RENT, rentCostMapper.getRentCostMaxNum()));
//                rentCost.setRentCostType(Constant.PERSON_TYPE_LANDLARD);
//                rentCost.setRentCostDate((Long) houseMap.get(dateKey));
////                缴纳状态 0-未交 1-已交
//                rentCost.setRentCostStatus((Boolean) houseMap.get(statusKey) ? 1 : 0);
//                rentCost.setRentCostMonth(Integer.valueOf(houseMap.get(costKey).toString()));
//                rentCost.setLeaseId(lease.getLeaseId());
//                rentCostService.save(rentCost);
//            } else {
//                break;
//            }
//        }
        return true;
    }

    private HouseOwner checkYzExist(HouseVO houseVO) {
        LambdaQueryWrapper<HouseOwner> houseOwnerLqw = Wrappers.<HouseOwner>lambdaQuery()
                .eq(HouseOwner::getHouseOwnerName, houseVO.getHouseOwnerName())
                .eq(HouseOwner::getHouseOwnerPhone, houseVO.getHouseOwnerPhone())
                .eq(HouseOwner::getUserId, UserContextHolder.getUserId())
                .eq(HouseOwner::getIsDelete, Constant.NO_DELETE);
        HouseOwner houseOwner = houseOwnerService.getOne(houseOwnerLqw);
        // 判断不存在,新增业主
        if (ObjectUtil.isEmpty(houseOwner)) {
            HouseOwner owner = new HouseOwner()
                    .setHouseOwnerId(UUIDGenerator.getNextId(HouseOwner.PREFIX, houseOwnerService.getMaxNum()))
                    .setHouseOwnerName(houseVO.getHouseOwnerName())
                    .setHouseOwnerPhone(houseVO.getHouseOwnerPhone())
                    .setHouseOwnerCard(houseVO.getHouseOwnerCard());
            houseOwnerService.save(owner);
            return owner;
        } else {
            return houseOwner;
        }
    }

    @Override
    public boolean delHouse(String idJson) {
        return this.removeById(idJson);
    }


    @Override
    public IPage<RoomRentDTO> findRoomRentList(RoomRentDTO roomRent) {
        log.info("userId:{}", UserContextHolder.getUserId());
        LambdaQueryWrapper<RoomRentDTO> lqw = new LambdaQueryWrapper<RoomRentDTO>()
                .eq(RoomRentDTO::getUserId, UserContextHolder.getUserId());

        Page<RoomRentDTO> pageBean = new Page<>(roomRent.getPageNo(), roomRent.getPageSize());
        return roomRentMapper.pageUser(pageBean, roomRent);
    }

    @Override
    public HouseVO getHouseVOById(String id) {
        // 查询房屋信息
        House house = this.getById(id);
        HouseVO houseVO = BeanUtil.toBean(house, HouseVO.class);
        // 查询房屋明细列表
        LambdaQueryWrapper<HouseDetail> lqw = new LambdaQueryWrapper<HouseDetail>()
                .eq(HouseDetail::getHouseId, id);

        List<HouseDetail> houseDetailList = houseDetailService.list(lqw);
        houseVO.setHouseDetailList(houseDetailList);
        return houseVO;
    }


}
