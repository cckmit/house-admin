package com.house.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.house.common.Constant;
import com.house.exception.CheckException;
import com.house.mapper.TenantMapper;
import com.house.model.Lease;
import com.house.model.Tenant;
import com.house.service.LeaseService;
import com.house.service.TenantService;
import com.house.utils.Query;
import com.house.utils.UUIDGenerator;
import com.house.utils.UserContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements TenantService {

    private LeaseService leaseService;

    @Autowired
    public void setLeaseService(LeaseService leaseService) {
        this.leaseService = leaseService;
    }


    @Override
    public List<Tenant> findTenantAll() {
        return baseMapper.findTenantAll();
    }

    @Override
    @Transactional
    public int delTenant(String idsJson) {
        Map<String, List> ids = new HashMap<>();
        List<String> idsList = ids.get("ids");
        int i = 0;
        for (String id : idsList) {
            if (baseMapper.findTenantIsStillRent(id) == 0) {
                int j = baseMapper.delTenant(id);
                i += j;
            } else {
                i = -1;
                break;
            }
        }
        return i;
    }


    @Override
    public List<Tenant> findTenantNoRent() {
        return baseMapper.findTenantNoRent();
    }

    @Override
    public IPage<Tenant> getTenantList(Map<String, Object> params, Tenant tenant) {
        QueryWrapper<Tenant> queryWrapper = new QueryWrapper<Tenant>()
                .like(StringUtils.isNotBlank(tenant.getTenantName()), "te.tenant_name", tenant.getTenantName())
                .eq(StringUtils.isNotBlank(tenant.getTenantPhone()), "te.tenant_phone", tenant.getTenantPhone())
                .eq(StringUtils.isNotBlank(tenant.getTenantCard()), "te.tenant_card", tenant.getTenantCard())
                .eq("te.is_delete", Constant.NO_DELETE)
                .eq("te.user_id", UserContextHolder.getUserId());


        return baseMapper.getTenantList(new Query<Tenant>().getPage(params), queryWrapper);
    }

    @Override
    public Boolean addOrUpdateTenant(Tenant tenant) {
        if (ObjectUtil.isEmpty(tenant.getTenantName())) {
            throw new CheckException("租客姓名为必填！");
        }
        if (ObjectUtil.isEmpty(tenant.getTenantPhone())) {
            throw new CheckException("租客手机号为必填！");
        }
        boolean isUpdate = StringUtils.isNotBlank(tenant.getTenantId());
        if (isUpdate) {
            Tenant tenantOld = this.getById(tenant.getTenantId());
            if (tenantOld == null) {
                throw new CheckException(String.format("编号为【%s】的租客已不存在!", tenant.getTenantId()));
            }
        }

        LambdaQueryWrapper<Tenant> tenantLqw = Wrappers.<Tenant>lambdaQuery()
                .eq(Tenant::getTenantName, tenant.getTenantName())
                .eq(Tenant::getTenantPhone, tenant.getTenantPhone())
                .ne(isUpdate, Tenant::getTenantId, tenant.getTenantId())
                .eq(Tenant::getUserId, UserContextHolder.getUserId());
        Tenant tenantRes = this.getOne(tenantLqw);
        if (tenantRes != null) {
            throw new CheckException("租客姓名与手机号已存在，请查询后再操作！");
        }

        if (isUpdate) {
            Tenant oldItem = this.getById(tenant.getTenantId());
            LambdaQueryWrapper<Lease> leaseLambdaQueryWrapper = Wrappers.<Lease>lambdaQuery()
                    .eq(Lease::getTenantId, tenant.getTenantId());

            if (ObjectUtil.isNotEmpty(leaseService.list(leaseLambdaQueryWrapper))) {
                if (!oldItem.getTenantName().equals(tenant.getTenantName())) {
                    throw new CheckException("租客姓名存在租约，不能修改租客姓名！");
                }
            }

            return this.updateById(tenant);
        } else {
            tenant.setTenantId(UUIDGenerator.getNextId(Tenant.TENANT_PREFIX, baseMapper.getTenantMaxNum()));
            return this.save(tenant);
        }
    }

    @Override
    public Boolean removeTenantById(String id) {
        if (ObjectUtil.isEmpty(id)) {
            throw new CheckException("租客ID参数为空！");
        }
        LambdaQueryWrapper<Lease> tenantLqw = Wrappers.<Lease>lambdaQuery()
                .eq(Lease::getTenantId, id);

        if (ObjectUtil.isNotEmpty(leaseService.list(tenantLqw))) {
            throw new CheckException("租客存在租约，请先解约！");
        }

        return this.removeById(id);
    }

}
