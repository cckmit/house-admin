package com.house.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.house.model.Routes;
import com.house.model.RoutesTree;

import java.util.List;

/**
 *
 */
public interface RoutesService extends IService<Routes> {

    List<RoutesTree> getRoutes();
}
