package com.house.controller;

import com.house.common.bean.ResultBean;
import com.house.common.enums.StatusEnum;
import com.house.model.CardNoInfo;
import com.house.model.DictCommon;
import com.house.service.CardNoService;
import com.house.service.DictCommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "信息查询")
@RestController
@RequestMapping("query")
public class SelectController {

    private CardNoService cardNoService;
    private DictCommonService dictCommonService;

    @Autowired
    public void setCardNoService(CardNoService cardNoService) {
        this.cardNoService = cardNoService;
    }

    @Autowired
    public void setDictCommonService(DictCommonService dictCommonService) {
        this.dictCommonService = dictCommonService;
    }

    @GetMapping("cardNo/{no}")
    public ResultBean<CardNoInfo> getCardNoInfo(@PathVariable("no") String certificateNo) {
        return ResultBean.ok(cardNoService.getCardNoInfo(certificateNo));
    }


    @Operation(summary = "小区列表")
    @GetMapping("communityList")
    public ResultBean<List<DictCommon>> communityList(@RequestParam("keyword") String value) {
        return new ResultBean<>(dictCommonService.dictCommonList(StatusEnum.COMMUNITY.getCode(), value));
    }
}
