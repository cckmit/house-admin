package com.house.controller;

import com.house.model.Grid;
import com.house.model.ResultBean;
import com.house.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("local")
public class LocalController {

    @Autowired
    private DataService dataService;

    /**
     * 获取轮播图
     */
    @GetMapping("slides")
    public ResultBean<List<String>> getJobInfo() {
        List<String> imgList = new ArrayList<String>(4) {{
            add("http://images.zabbix.store/sliders/wallhaven-4dv2pj.jpeg");
            add("http://images.zabbix.store/sliders/wallhaven-4o7w35.jpeg");
            add("http://images.zabbix.store/sliders/wallhaven-nm6vrk.jpeg");
            add("http://images.zabbix.store/sliders/wallhaven-x1degz.jpeg");
        }};
        return ResultBean.ok(imgList);
    }

    @GetMapping("gridList")
    public ResultBean<List<Grid>> getGridList() {
        return ResultBean.ok(dataService.getGridList());
    }


}
