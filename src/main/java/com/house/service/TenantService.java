package com.house.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.model.Tenant;


import java.util.List;
import java.util.Map;

public interface TenantService extends IService<Tenant> {

    List<Tenant> findTenantAll();

    int delTenant(String idsList);

    List<Tenant> findTenantNoRent();

    IPage<Tenant> getTenantList(Map<String, Object> params, Tenant tenant);

    Boolean addOrUpdateTenant(Tenant tenant);

    Boolean removeTenantById(String id);
}
