package com.house.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.house.common.bean.ResultBean;
import com.house.model.Finance;
import com.house.model.vo.FinanceVO;
import com.house.service.FinanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "租金流水")
@RestController
@RequestMapping("/api/v1/finance")
public class FinanceController {

    private FinanceService financeService;

    @Autowired
    public void setFinanceService(FinanceService financeService) {
        this.financeService = financeService;
    }

    @Operation(summary = "新增租金信息")
    @PostMapping(value = "add")
    public ResultBean<Boolean> add(@RequestBody FinanceVO vo) {
        return ResultBean.ok(financeService.add(vo));
    }

    @Operation(summary = "租金列表")
    @PostMapping(value = "/list")
    public ResultBean<List<Finance>> financeList(@RequestBody FinanceVO vo) {
        return ResultBean.ok(financeService.getFinanceList(vo));
    }

    @Operation(summary = "租金流水分页")
    @PostMapping(value = "/page")
    public ResultBean<IPage<Finance>> pageList(@RequestBody FinanceVO vo) {
        return ResultBean.ok(financeService.pageList(vo));
    }

}
