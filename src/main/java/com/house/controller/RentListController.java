package com.house.controller;

import com.house.common.bean.PageResultBean;
import com.house.common.bean.ResultBean;
import com.house.model.HouseOwner;
import com.house.model.RentList;
import com.house.model.vo.RentListVO;
import com.house.service.RentListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "租单管理")
@RestController
@RequestMapping("rentList")
public class RentListController {

    private RentListService rentListService;

    @Autowired
    public void setRentListService(RentListService rentListService) {
        this.rentListService = rentListService;
    }

    @Operation(summary = "新增或更新租金信息")
    @PostMapping("addOrUpdate")
    public ResultBean<Boolean> addOrUpdateRentInfo(@RequestBody RentListVO rentListVO) {
        return ResultBean.ok(rentListService.addOrUpdateRentInfo(rentListVO));
    }

    @Operation(summary = "获取租金列表")
    @PostMapping("list")
    public PageResultBean<List<RentListVO>> getRentList(@RequestBody RentListVO vo) {
        return PageResultBean.buildIPage(rentListService.getRentList(vo));
    }

    @Operation(summary = "get by id for rent info")
    @PostMapping("getById")
    public ResultBean<RentListVO> getById(@RequestBody RentListVO vo) {
        return ResultBean.ok(rentListService.getByIdForRentList(vo));
    }

    @Operation(summary = "获取当月金额")
    @PostMapping("dash")
    public ResultBean<Map<String,Long>> getCurrentMonthMoney() {
        return ResultBean.ok(rentListService.getCurrentMonthMoney());
    }

    @Operation(summary = "删除租单")
    @GetMapping("removeById/{id}")
    public ResultBean<Boolean> removeRentInfoById(@PathVariable("id") Integer id) {
        return ResultBean.ok(rentListService.removeRentInfoById(id));
    }

}
