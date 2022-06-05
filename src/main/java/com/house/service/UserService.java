package com.house.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.house.model.ReqUserInfo;
import com.house.model.UserInfo;
import com.taobao.api.ApiException;

import java.util.Map;

public interface UserService extends IService<UserInfo> {
    Map<String, Object> wxLogin(ReqUserInfo reqUserInfo) throws Exception;

    UserInfo getUserInfo();

    UserInfo getCurrentUserInfo();

    Boolean logout();

    UserInfo registerUser(UserInfo userInfo);

    Boolean getSmsCode(String userPhone, String type) throws Exception;

    UserInfo loginByPhone(UserInfo admin);

    int getMaxNum();

    IPage<UserInfo> getAccountList(Map<String, Object> params, UserInfo vo);

    Boolean checkUserRoleUse(String roleId);

    /**
     * 新增或更新用户
     */
    Boolean addOrUpdate(UserInfo userInfo);

    Boolean delUserById(String userId);

    /**
     * 更新角色状态
     */
    Boolean updateEnableSetting(UserInfo userId);

    String getDingTalkUid(String phone) throws ApiException;

    /**
     * 设置钉钉uid绑定用户
     */
    Boolean setDingTalkUid(UserInfo user);
}
