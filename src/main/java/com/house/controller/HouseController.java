package com.house.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.common.bean.PageResultBean;
import com.house.common.bean.ResultBean;
import com.house.common.enums.StatusEnum;
import com.house.model.DictCommon;
import com.house.model.dto.RoomRentDTO;
import com.house.model.vo.HouseVO;
import com.house.service.DictCommonService;
import com.house.service.HouseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Tag(name = "房源管理")
@CrossOrigin
@RestController
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private DictCommonService dictCommonService;

    private HouseService houseService;

    @Autowired
    public void setHouseService(HouseService houseService) {
        this.houseService = houseService;
    }

    @Operation(summary = "房源列表")
    @GetMapping("/list")
    public PageResultBean<List<HouseVO>> listHouseDetail(@Parameter(hidden = true) @RequestParam Map<String, Object> params,
                                                         HouseVO vo) {
        return PageResultBean.buildIPage(houseService.getHouseList(params, vo));
    }

    @GetMapping(value = "getById/{id}")
    public ResultBean<HouseVO> getById(@PathVariable("id") String id) {
        return ResultBean.ok(houseService.getHouseVOById(id));
    }

    @Operation(summary = "新增房产信息")
    @PostMapping(value = "add")
    public ResultBean<Boolean> addHouse(@RequestBody HouseVO house) {
        return ResultBean.ok(houseService.addHouseVO(house));
    }

    @PostMapping(value = "update")
    public ResultBean<Boolean> updHouse(@RequestBody HouseVO house) {
        return new ResultBean<>(houseService.updHouse(house));
    }

    @DeleteMapping("removeById/{id}")
    public ResultBean<Boolean> delHouse(@PathVariable String id) {
        return new ResultBean<>(houseService.delHouse(id));
    }

    @GetMapping("rentAll")
    public ResultBean<IPage<RoomRentDTO>> findRoomRentDTOList(RoomRentDTO roomRent) {
        return new ResultBean<>(houseService.findRoomRentList(roomRent));
    }

}
