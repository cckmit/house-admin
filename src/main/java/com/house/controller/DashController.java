package com.house.controller;

import cn.hutool.core.math.Calculator;
import com.house.common.bean.ResultBean;
import com.house.mapper.HouseDetailMapper;
import com.house.model.GrowCardItem;
import com.house.utils.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("dashboad")
@RestController
public class DashController {

    @Autowired
    private HouseDetailMapper houseDetailMapper;


    @GetMapping("info")
    public ResultBean<List<GrowCardItem>> getRoomInfo() {
        List<GrowCardItem> growCardItemList = new ArrayList<>();
        String userId = UserContextHolder.getUserId();
        Integer offHouseDetail = houseDetailMapper.getHouseDetailOffByUserId(userId);
        Integer countHouseDetail = houseDetailMapper.getHouseDetailCountByUserId(userId);
        Integer onHouseDetail = countHouseDetail-offHouseDetail;
        Double rate = 0.00;
        if(onHouseDetail > 0){
            double rateTemp = Calculator.conversion("1-("+onHouseDetail+"/"+countHouseDetail+")");
            rate =  rateTemp * 100;

        }
        growCardItemList.add(GrowCardItem.builder()
                .titleMax("待出租房源数量")
                .titleMin("总房源数量")
                .icon("flat-color-icons:home")
                .value(countHouseDetail-offHouseDetail)
                .total(countHouseDetail)
                .color("green")
                .action("出租率:"+rate.intValue()+"%")
                .build());
        return new ResultBean<>(growCardItemList);
    }
}
