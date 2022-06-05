package com.house.controller;

import com.house.common.bean.ResultBean;
import com.house.model.vo.SecureSettingVO;
import com.house.utils.UserContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/secure")
public class SecureSettingController {
    @GetMapping("/info")
    public ResultBean<List<SecureSettingVO>> getSecureSettingList() {
        List<SecureSettingVO> settings = new ArrayList<>();
        settings.add(SecureSettingVO.builder()
                .key("phone")
                .title("手机绑定")
                .extra("修改")
                .description(UserContextHolder.getUserInfo().getUserPhone())
                .build());
        settings.add(SecureSettingVO.builder()
                .key("dingtalk")
                .title("钉钉绑定")
                .extra("修改")
                .description(UserContextHolder.getUserInfo().getDingtalkUid())
                .build());
        return new ResultBean<>(settings);
    }
}
