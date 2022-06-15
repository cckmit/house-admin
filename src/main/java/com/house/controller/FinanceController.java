package com.house.controller;

import com.house.common.bean.ResultBean;
import com.house.model.vo.FinanceVO;
import com.house.service.FinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "租金流水")
@RestController
@RequestMapping("/api/v1/finance")
public class FinanceController {

    private FinanceService financeService;

    @Autowired
    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }

    @Operation(summary = "新增房产信息")
    @PostMapping(value = "add")
    public ResultBean<Boolean> add(@RequestBody FinanceVO vo) {
        return ResultBean.ok(financeService.add(vo));
    }

}
