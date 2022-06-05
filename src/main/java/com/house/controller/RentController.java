package com.house.controller;

import com.house.common.bean.PageResultBean;
import com.house.common.bean.ResultBean;
import com.house.mapper.RentCostMapper;
import com.house.model.vo.RentCostDashboadVO;
import com.house.model.vo.RentOwnerVO;
import com.house.model.vo.RentTenantVO;
import com.house.model.vo.RentVO;
import com.house.service.RentCostService;
import com.house.utils.UserContextHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "租金列表")
@RestController
@RequestMapping("rent")
public class RentController {

    @Autowired
    private RentCostMapper rentCostMapper;

    private RentCostService rentCostService;

    @Autowired
    public void setRentCostService(RentCostService rentCostService) {
        this.rentCostService = rentCostService;
    }

    @Operation(summary = "房东租金列表")
    @GetMapping("ownerList")
    public PageResultBean<List<RentOwnerVO>> getRentListByOwner(@RequestParam Map<String, Object> params,
                                                                RentOwnerVO vo) {
        return PageResultBean.buildIPage(rentCostService.getOwnerRentList(params, vo));
    }

    @Operation(summary = "租客租金列表")
    @GetMapping("tenantList")
    public PageResultBean<List<RentTenantVO>> getRentListByTenant(@RequestParam Map<String, Object> params,
                                                                  RentTenantVO vo) {
        return PageResultBean.buildIPage(rentCostService.getTenantRentList(params, vo));
    }

    @Operation(summary = "新增或更新租金")
    @PostMapping("addOrUpdate")
    public ResultBean<Boolean> addOrUpdateRent(@RequestBody RentVO vo) {
        return ResultBean.ok(rentCostService.addOrUpdateRent(vo));
    }

    @Operation(summary = "获取近期应收应付")
    @GetMapping("getRecentRentCost")
    public ResultBean<List<RentCostDashboadVO>> getRecentRentCost() {
        return ResultBean.ok(rentCostMapper.getRecentRentCost(UserContextHolder.getUserId()));
    }

}
