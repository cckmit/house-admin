package com.house.service.impl;


import com.house.common.properties.WechatProperties;
import com.house.service.WeChatService;
import com.house.utils.Jcode2SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * 微信业务实现类
 */
@Service
@Slf4j
@Transactional
public class WeChatServiceImpl implements WeChatService {

    @Resource
    private WechatProperties wechatProperties;

//    @Override
//    public GenericResponse wxLogin(String code) throws Exception{
////        JSONObject sessionInfo = JSONObject.parseObject(jcode2Session(code));
////        log.info("sessionInfo ==="+sessionInfo.toJSONString());
////        Assert.notNull(sessionInfo,"code 无效");
////
////        Assert.isTrue(!StringUtils.isEmpty(sessionInfo.getString("openid")),"open_id为空");
//////
////        // 获取用户唯一标识符 openid成功
////        // 模拟从数据库获取用户信息
////        User user = new User();
////        user.setId(1L);
////        Set authoritiesSet = new HashSet();
////        // 模拟从数据库中获取用户权限
////        authoritiesSet.add(new SimpleGrantedAuthority("test:add"));
////        authoritiesSet.add(new SimpleGrantedAuthority("test:list"));
////        authoritiesSet.add(new SimpleGrantedAuthority("ddd:list"));
////        user.setAuthorities(authoritiesSet);
////        HashMap<String,Object> hashMap = new HashMap<>();
////        hashMap.put("id",user.getId().toString());
////        hashMap.put("authorities",authoritiesSet);
////        String token = JwtTokenUtil.generateToken(user);
////        redisUtil.hset(token,hashMap);
////
////        return GenericResponse.response(ServiceError.NORMAL,token);
//        return null;
//    }

//    @Override
//    public String getAccessToken() {
//        JSONObject sessionInfo = JSONObject.parseObject(Jcode2SessionUtil.getAccessToken(appid,secret));
//        Assert.notNull(sessionInfo,"code 无效");
//        String accessToken=(String)redisUtil.get("accessToken");
//        if(StringUtils.isEmpty(accessToken)){
//            accessToken=sessionInfo.getString("access_token");
//            redisUtil.set("accessToken",accessToken,7200);
//        }
//        return accessToken;
//    }
//
//    @Override
//    public boolean createQRCode(String scene,String fileName) {
//        String token= getAccessToken();
//        return Jcode2SessionUtil.getUnlimited(token,scene,null,width,autoColor,fileName,isHyaline);
//    }

    @Override
    public String getAccessToken() {
        return null;
    }

    @Override
    public boolean createQRCode(String scene, String fileName) {
        return false;
    }

    /**
     * 登录凭证校验
     */
    @Override
    public String jcode2Session(String code) throws Exception {
        return Jcode2SessionUtil.jscode2session(wechatProperties.getAppid(), wechatProperties.getSecret(), code, "authorization_code");//登录grantType固定
    }
}