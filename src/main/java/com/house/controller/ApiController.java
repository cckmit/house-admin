package com.house.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.house.exception.CheckException;
import com.house.model.Common;
import com.house.model.ResultBean;
import com.house.model.WarnAddress;
import com.house.service.UrlListService;
import com.house.service.UrlShService;
import com.house.service.WarnAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private UrlListService urlListService;
    @Autowired
    private UrlShService urlShService;

    @Autowired
    private WarnAddressService warnAddressService;

    @PostMapping("loadData")
    public ResultBean<Boolean> loadData(@RequestBody Map<String, String> map) throws InterruptedException {
        this.checkToken(map);
        return ResultBean.ok(urlShService.loadData());
    }

    @GetMapping("init")
    public ResultBean<Boolean> getJobInfo() {
        return ResultBean.ok(urlListService.getGrUrl(Common.URL_WJW, false));
    }

    @PostMapping("search")
    public ResultBean<String> search(@RequestBody Map<String, String> map) {
        this.checkToken(map);
        LambdaQueryWrapper<WarnAddress> warnAddressLqw = Wrappers.<WarnAddress>lambdaQuery()
                .eq(WarnAddress::getAddress, map.get("address"));
        List<WarnAddress> warnAddressList = warnAddressService.list(warnAddressLqw);

        if (ObjectUtils.isEmpty(warnAddressList)) {
            return ResultBean.ok("地址暂未查询到感染情况.");
        } else {
            String endStr = "</strong></span></li>";
            StringBuilder str = new StringBuilder();
            str.append("<ul style='padding: 0;margin: 0;text-align: left;list-style-type: none;'>")
                    .append("<li>区域:<span style='color:#D0104C;'><strong>")
                    .append(warnAddressList.get(0).getRegion())
                    .append(endStr)
                    .append("<li>地址:<span style='color:#D0104C;'><strong>")
                    .append(warnAddressList.get(0).getAddress())
                    .append(endStr);
            DateTimeFormatter dfDateTime = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            str.append("<li>新增确诊或无症状感染者的时间如下</li>")
                    .append("<ol style='padding: 0;margin: 0;text-align: left;list-style-type: none;'>");
            Collections.reverse(warnAddressList);
            for (WarnAddress warnAddress : warnAddressList) {
                Instant instant = warnAddress.getConfirmDate().toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
                str.append("<li><span style='color:#D0104C;'><strong>")
                        .append(dfDateTime.format(localDateTime))
                        .append(endStr);
            }
            str.append("</ol></ul>");
            return ResultBean.ok(str.toString());
        }
    }

    private void checkToken(Map<String, String> map) {
        if (ObjectUtils.isEmpty(map)
                || map.get("token") == null) {
            throw new CheckException("无权限访问~");
        }
    }
}