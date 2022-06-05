package com.house.controller;

import com.house.common.bean.PageResultBean;
import com.house.common.bean.ResultBean;
import com.house.model.Lease;
import com.house.model.vo.LeaseOwnerVO;
import com.house.model.vo.LeaseRequestVO;
import com.house.model.vo.LeaseTenantVO;
import com.house.model.vo.LeaseVO;
import com.house.service.LeaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "租约模块")
@RestController
@RequestMapping("lease")
public class LeaseController {

    private LeaseService leaseService;

    @Autowired
    public void setLeaseService(LeaseService leaseService) {
        this.leaseService = leaseService;
    }

    @Operation(summary = "租客租约列表")
    @GetMapping("tenantList")
    public PageResultBean<List<LeaseTenantVO>> getLeaseList(@RequestParam Map<String, Object> params,
                                                            LeaseTenantVO vo) {
        return PageResultBean.buildIPage(leaseService.getTenantLeaseList(params, vo));
    }

    @Operation(summary = "房东租约列表")
    @GetMapping("ownerList")
    public PageResultBean<List<LeaseOwnerVO>> getOwnerLeaseList(@RequestParam Map<String, Object> params,
                                                                LeaseOwnerVO vo) {
        return PageResultBean.buildIPage(leaseService.getOwnerLeaseList(params, vo));
    }

    @Operation(summary = "新增租客租约")
    @PostMapping(value = "addLease")
    public ResultBean<Boolean> addLease(@RequestBody Map<String, String> leaseMap) {
        return ResultBean.ok(leaseService.addLease(leaseMap));
    }

    @Operation(summary = "更新租约")
    @PostMapping(value = "updateLease")
    public ResultBean<Boolean> updateLease(@RequestBody LeaseVO leaseVO) {
        return ResultBean.ok(leaseService.updateLease(leaseVO));
    }

    @Operation(summary = "根据ID删除租约")
    @DeleteMapping("removeByLeaseId/{id}")
    public ResultBean<Boolean> removeHouseDetail(@PathVariable("id") String id) {
        return new ResultBean<>(leaseService.removeById(id));
    }

    @Operation(summary = "解除租约")
    @PostMapping("cancel")
    public ResultBean<Boolean> reversoContextById(@RequestBody LeaseRequestVO leaseRequestVO) {
        return new ResultBean<>(leaseService.reversoContextById(leaseRequestVO));
    }

    @Operation(summary = "解除业主租约")
    @GetMapping("rcByLeaseId/{leaseId}")
    public ResultBean<Boolean> rcByLeaseId(@PathVariable("leaseId") String leaseId) {
        return new ResultBean<>(leaseService.rcByLeaseId(leaseId));
    }

    @Operation(summary = "根据ID获取租约")
    @GetMapping("getLeaseById/{leaseId}")
    public ResultBean<Lease> getLeaseById(@PathVariable("leaseId") String leaseId) {
        return new ResultBean<>(leaseService.getLeaseById(leaseId));
    }

}
