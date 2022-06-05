package com.house.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.common.Constant;
import com.house.exception.CheckException;
import com.house.mapper.HouseDetailMapper;
import com.house.model.HouseDetail;
import com.house.model.RentCost;
import com.house.model.SelectModel;
import com.house.model.vo.HouseDetailVO;
import com.house.model.vo.RoomInfo;
import com.house.utils.DateUtils;
import com.house.utils.Query;
import com.house.utils.UUIDGenerator;
import com.house.utils.UserContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HouseDetailService extends ServiceImpl<HouseDetailMapper, HouseDetail> {

    @Autowired
    private RentCostService rentCostService;
    
    public IPage<HouseDetailVO> getHouseDetailList(Map<String, Object> params, HouseDetailVO vo) {
        QueryWrapper<HouseDetail> queryWrapper = new QueryWrapper<HouseDetail>()
                .like(StringUtils.isNotBlank(vo.getHouseDetailName()), "hd.house_detail_name", vo.getHouseDetailName())
                .eq(StringUtils.isNotBlank(vo.getHouseId()), "hd.house_id", vo.getHouseId())
                .eq(StringUtils.isNotBlank(vo.getHouseDetailId()), "hd.house_detail_id", vo.getHouseDetailId())
                .eq(StringUtils.isNotBlank(vo.getHdStatus()), "hd.hd_status", vo.getHdStatus())
                .eq("hd.is_delete", Constant.NO_DELETE)
                .eq("hd.user_id", UserContextHolder.getUserId());


        return baseMapper.getHouseDetailList(new Query<HouseDetail>().getPage(params), queryWrapper);
    }


    public Boolean addOrUpdate(HouseDetailVO vo) {
        if (StringUtils.isBlank(vo.getHouseDetailName())) {
            throw new CheckException("房产明细单间名称必填！");
        }

        LambdaQueryWrapper<HouseDetail> lqw = Wrappers.<HouseDetail>lambdaQuery()
                .eq(HouseDetail::getHouseDetailName, vo.getHouseDetailName().trim());

        if (ObjectUtil.isEmpty(vo.getHouseDetailId())) {
            vo.setHouseDetailId(UUIDGenerator.getNextId(HouseDetail.PREFIX_HOUSE_DETAIL
                    , baseMapper.getHouseDetailMaxNum(DateUtils.getFristOfMonth())));
        }
        lqw.ne(HouseDetail::getHouseDetailId, vo.getHouseDetailId());
        if (ObjectUtil.isNotEmpty(this.list(lqw))) {
            throw new CheckException("房产明细单间名称重复！");
        }

        HouseDetail houseDetail = BeanUtil.toBean(vo, HouseDetail.class);

        return this.saveOrUpdate(houseDetail);
    }


    public Boolean removeHouseDetail(String id) {
        // 存在租约无法删除
        HouseDetail houseDetail = this.getById(id);
        if (ObjectUtil.isEmpty(houseDetail)) {
            throw new CheckException(String.format("编号为【%s】的已不存在，请刷新！", id));
        }
        if (StringUtils.isNotBlank(houseDetail.getTenantLeaseId())) {
            throw new CheckException(String.format("房间存在租约，请先退房..."));
        }
        return this.removeById(id);
    }


    public List<SelectModel> getSelectModel(String hdName) {
        LambdaQueryWrapper<HouseDetail> houseLq = Wrappers.<HouseDetail>lambdaQuery()
                .like(StringUtils.isNotBlank(hdName), HouseDetail::getHouseDetailName, hdName)
                .eq(HouseDetail::getUserId, UserContextHolder.getUserId());
        List<HouseDetail> houseDetailList = this.list(houseLq);

        List<SelectModel> selectModelList = new ArrayList<>();
        houseDetailList.forEach(hd -> {
            SelectModel selectModel = SelectModel.builder()
                    .key(hd.getHouseDetailId())
                    .label(hd.getHouseDetailName())
                    .value(hd.getHouseDetailId())
                    .build();
            selectModelList.add(selectModel);
        });

        return selectModelList;
    }


    public RoomInfo getRoomInfo(String id) {
        RoomInfo roomInfo = baseMapper.getRoomInfo(id);
        LambdaQueryWrapper<RentCost> rentCostLqw = Wrappers.<RentCost>lambdaQuery()
                .eq(RentCost::getLeaseId, roomInfo.getTenantLeaseId());
        List<RentCost> costList = rentCostService.list(rentCostLqw);
        roomInfo.setCostList(costList);

        return roomInfo;
    }

}