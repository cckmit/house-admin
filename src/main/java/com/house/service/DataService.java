package com.house.service;

import com.house.model.Grid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataService {

    public List<Grid> getGridList() {
        List<Grid> gridList = new ArrayList<>();
        gridList.add(new Grid(1, "感染记录", "http://images.zabbix.store/sliders/search.png"));
        gridList.add(new Grid(2, "足浴城", "http://images.zabbix.store/sliders/demo.png"));
        gridList.add(new Grid(3, "美食", "http://images.zabbix.store/sliders/demo.png"));
        gridList.add(new Grid(4, "KTV", "http://images.zabbix.store/sliders/demo.png"));
        gridList.add(new Grid(5, "装修", "http://images.zabbix.store/sliders/demo.png"));
        gridList.add(new Grid(6, "租房", "http://images.zabbix.store/sliders/demo.png"));
        gridList.add(new Grid(7, "超市", "http://images.zabbix.store/sliders/demo.png"));
        gridList.add(new Grid(8, "找工作", "http://images.zabbix.store/sliders/demo.png"));
        gridList.add(new Grid(9, "兼职", "http://images.zabbix.store/sliders/demo.png"));
        return gridList;
    }
}
