package com.house.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.house.model.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;



public interface UserMapper extends BaseMapper<UserInfo> {

    @Select("select * from user where username = #{username} and is_delete = 0")
    UserInfo findByUsername(String username);

    int getUserMaxNum();

    IPage<UserInfo> getAccountList(IPage<UserInfo> page, @Param(Constants.WRAPPER) QueryWrapper<UserInfo> queryWrapper);

    int checkUserRoleUse(@Param("roleId") String roleId);

    Integer updateEnableSetting(@Param("userId") String userId, @Param("uxEnabled") Boolean uxEnabled);

    void setDingTalkUid(@Param("userInfo") UserInfo userInfo);

//
//    /**
//     * 查询所有登录用户数
//     * @return
//     */
//    @Select("select id,username,'********' password,createdate,changedate from user")
//    List<User> findUserData();
//
//    /**
//     * 新增登录用户
//     * @param user
//     * @return
//     */
//    @Insert("insert into user (id,username,password,createdate,changedate) values (#{id},#{username},#{password},#{createdate},#{changedate})")
//    int addUser(User user);
//
//    /**
//     * 根据用户名称查询用户总数
//     * @param user
//     * @return
//     */
////    @Select("<script> select count(1) from user where username = #{username} <if test='id!=null'> and id !=#{id} </if> </script>")
////    int findCountByName(User user);
//
//    /**
//     * 根据id查询用户
//     * @param id
//     * @return
//     */
////    @Select("select * from user where id = #{id}")
////    User findUserById(String id);
//
//    /**
//     * 更新登录用户
//     * @param user
//     * @return
//     */
//    @Update("update user set username = #{username},password=#{password},changedate=#{changedate} where id = #{id}")
//    int updUser(User user);
//
//    /**
//     * 删除登录用户
//     * @param user
//     * @return
//     */
//    @Delete("delete from user where id = #{id}")
//    int delUserById(User user);

}
