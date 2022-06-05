package com.house.controller;

import com.house.common.bean.PageResultBean;
import com.house.common.bean.ResultBean;
import com.house.model.SelectModel;
import com.house.model.vo.HouseDetailVO;
import com.house.model.vo.RoomInfo;
import com.house.service.HouseDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


//@Tag(name = "房源明细")
@Tag(name = "房源明细")
@RestController
@RequestMapping("houseDetail")
public class HouseDetailController {

    private HouseDetailService houseDetailService;

    @Autowired
    public void setHouseDetailService(HouseDetailService houseDetailService) {
        this.houseDetailService = houseDetailService;
    }

    @Operation(summary = "房源明细列表")
    @GetMapping("/list")
    public PageResultBean<List<HouseDetailVO>> listHouseDetail(@RequestParam Map<String, Object> params,
                                                               HouseDetailVO vo) {
        return PageResultBean.buildIPage(houseDetailService.getHouseDetailList(params, vo));
    }

    @Operation(summary = "房源明细下拉菜单")
    @GetMapping("/select")
    public ResultBean<List<SelectModel>> getSelectModel(String keyword) {
        return ResultBean.ok(houseDetailService.getSelectModel(keyword));
    }

    @Operation(summary = "房产明细更新或新增")
    @PostMapping("addOrUpdate")
    public ResultBean<Boolean> addOrUpdate(@RequestBody HouseDetailVO vo) {
        return ResultBean.ok(houseDetailService.addOrUpdate(vo));
    }

    @Operation(summary = "删除房间")
    @DeleteMapping("removeById/{id}")
    public ResultBean<Boolean> removeHouseDetail(@PathVariable String id) {
        return new ResultBean<>(houseDetailService.removeHouseDetail(id));
    }

    @Operation(summary = "获取房间详细信息")
    @GetMapping("getInfoById/{id}")
    public ResultBean<RoomInfo> getRoomInfo(@PathVariable String id) {
        return new ResultBean<>(houseDetailService.getRoomInfo(id));
    }

}
