package com.house.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.house.model.Tenant;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;



public interface TenantMapper extends BaseMapper<Tenant> {

    /**
     * 查询所有租客
     */
    @Select("select * from tenant")
    List<Tenant> findTenantAll();

    /**
     * 新增租客
     */
    @Insert("insert into tenant (id,tenant_name,tenant_phone,tenant_sfznum,sex) values (#{id},#{tenant_name},#{tenant_phone},#{tenant_sfznum},#{sex})")
    int addTenant(Tenant tenant);

    /**
     * 更新租客
     */
    @Update("update tenant set tenant_name = #{tenant_name} ,tenant_phone = #{tenant_phone}, tenant_sfznum = #{tenant_sfznum} ,sex = #{sex} where id = #{id}")
    int updTenant(Tenant tenant);

    /**
     * 删除租客
     */
    @Delete("delete from tenant where id = #{id}")
    int delTenant(String id);

    /**
     * 根据租客id查询租客
     */
    @Select("select * from tenant where id = #{id}")
    Tenant findTenantById(String id);

    /**
     * 查询租客是否已经租了房子
     */
    @Select("select count(1) from room where room_tenant_id = #{id}")
    int findTenantIsStillRent(String id);

    /**
     * 查询未租房的租客
     */
    @Select("select * from tenant where id not in (select distinct room_tenant_id from room where room_tenant_id is not null )")
    List<Tenant> findTenantNoRent();

    IPage<Tenant> getTenantList(IPage<Tenant> page, @Param(Constants.WRAPPER) Wrapper<Tenant> wrapper);

    int getTenantMaxNum();
}
