package com.house.controller;

import com.house.common.bean.ResultBean;
import com.house.common.bean.PageResultBean;
import com.house.model.UserInfo;
import com.house.service.UserService;
import com.taobao.api.ApiException;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;


    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/info")
    public ResultBean<UserInfo> userInfo() {
        return new ResultBean<>(userService.getUserInfo());
    }

    @Operation(summary ="用户列表")
    @GetMapping("getAccountList")
    public PageResultBean<List<UserInfo>> getAccountList( @RequestParam Map<String, Object> params,
                                                         UserInfo vo) {
        return PageResultBean.buildIPage(userService.getAccountList(params, vo));
    }

    @PostMapping("/logout2")
    public ResultBean<Boolean> logout() {
        return new ResultBean<>(userService.logout());
    }

    @Operation(summary = "保存或更新用户")
    @PostMapping("addOrUpdate")
    public ResultBean<Boolean> addOrUpdate(@RequestBody UserInfo userInfo) {
        return ResultBean.ok(userService.addOrUpdate(userInfo));
    }

    @RequestMapping("/removeById/{id}")
    public ResultBean<Boolean> removeById(@PathVariable("id") String userId) {
        return ResultBean.ok(userService.delUserById(userId));
    }

    @Operation(summary = "修改用户状态")
    @PostMapping("/enableSetting")
    public ResultBean<Boolean> enableSetting(@RequestBody UserInfo user) {
        return ResultBean.ok(userService.updateEnableSetting(user));
    }

    @Operation(summary = "获取钉钉账号")
    @GetMapping("/getDingTalkUid/{phone}")
    public ResultBean<String> getDingTalkUid(@PathVariable("phone")String phone) throws ApiException {
        return ResultBean.ok(userService.getDingTalkUid(phone));
    }

    @Operation(summary = "设置钉钉账号")
    @PostMapping("/setDingTalkUid")
    public ResultBean<Boolean> setDingTalkUid(@RequestBody UserInfo user){
        return ResultBean.ok(userService.setDingTalkUid(user));
    }
}
