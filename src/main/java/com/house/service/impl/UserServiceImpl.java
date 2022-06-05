package com.house.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.house.common.Constant;
import com.house.common.enums.ResultCode;
import com.house.common.properties.WechatProperties;
import com.house.exception.CheckException;
import com.house.mapper.UserMapper;
import com.house.model.ReqUserInfo;
import com.house.model.UserInfo;
import com.house.service.DingTalkService;
import com.house.service.SendSMS;
import com.house.service.UserService;
import com.house.service.WeChatService;
import com.house.utils.*;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, UserInfo> implements UserService, UserDetailsService {

    private final static String ID_PREFIX = "U";

    @Autowired
    private DingTalkService dingTalkService;
    @Autowired
    private WeChatService weChatService;
    @Autowired
    private SendSMS sendSMS;
    @Autowired
    private JwtTokenUtils jwtTokenProvider;
//    @Resource
//    private RedisUtils redisUtils;
    @Resource
    private WechatProperties wechatProperties;

    @Override
    public UserInfo loadUserByUsername(String username) {
        UserInfo userInfo = baseMapper.findByUsername(username);
//        if (userInfo == null) {
//            throw new CheckException(ResultCode.PERMISSION_NO_ACCESS);
//        }
        return userInfo;
    }

    @Override
    public Map<String, Object> wxLogin(ReqUserInfo reqUserInfo) throws Exception {
        //通过微信编号登陆
        if (StringUtils.isNotEmpty(reqUserInfo.getWxCode())) {
            JSONObject jsonObject = JSON.parseObject(weChatService.jcode2Session(reqUserInfo.getWxCode()));
            String openid = jsonObject.getString("openid");
            String sessionKey = jsonObject.getString("session_key");

            if (jsonObject.containsKey("errcode")) {
                log.error("错误代码与错误信息:{}", jsonObject.toJSONString());
                throw new CheckException(ResultCode.MINIP_ERROR);
            }

            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("wx_open_id", openid);
            String deCodeStr = WeChatUtil.decrypt(wechatProperties.getAppid(), sessionKey, reqUserInfo.getEncryptedData(), reqUserInfo.getIv());
            JSONObject wechatInfo = JSON.parseObject(deCodeStr);
            log.info("获取到的登录信息:{}", wechatInfo);
            UserInfo ptpUserInfo = baseMapper.selectOne(queryWrapper);
            if (ptpUserInfo == null) {
                ptpUserInfo = new UserInfo();
                String userId = UUIDGenerator.getNextId(UserInfo.USER_PREFIX, baseMapper.getUserMaxNum());
                ptpUserInfo.setUserId(userId);
                ptpUserInfo.setUsername(userId);
                ptpUserInfo.setWxOpenId(openid);
                ptpUserInfo.setCreateName("微信注册");
                ptpUserInfo.setCreateTime(DateUtils.getNowTimeStamp());
                ptpUserInfo.setUxEnabled(true);
                ptpUserInfo.setPassword(new BCryptPasswordEncoder().encode("123456"));
                ptpUserInfo.setAvatar(wechatInfo.get("avatarUrl").toString());
                this.save(ptpUserInfo);
            }
            ptpUserInfo.setSessionKey(sessionKey);
            String token = jwtTokenProvider.generateToken(ptpUserInfo.getUsername());
            log.info("=====>" + JSON.toJSONString(ptpUserInfo));
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            result.put("userInfo", ptpUserInfo);
            return result;
        } else {
            throw new AuthenticationException("未获取到微信编号") {
            };
        }
    }

    @Override
    public UserInfo getUserInfo() {
        UserInfo userInfo = this.getCurrentUserInfo();
//        userInfo.setRoles(Stream.of(new Role("Super Admin", "super")).collect(Collectors.toSet()));

        return userInfo;
    }


    /**
     * 获取当前登录用的的Id
     */
    @Override
    public UserInfo getCurrentUserInfo() {
        return (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public Boolean logout() {
        return null;
    }

    @Override
    public UserInfo registerUser(UserInfo userInfo) {
        // 查询重复
        LambdaQueryWrapper<UserInfo> lqw = Wrappers.<UserInfo>lambdaQuery()
                .eq(UserInfo::getUsername, userInfo.getUsername());
        UserInfo userCheck = baseMapper.selectOne(lqw);
        if (ObjectUtil.isNotEmpty(userCheck)) {
            throw new CheckException("用户名已存在");
        }
        // 验证手机号
        LambdaQueryWrapper<UserInfo> userInfoLqw = Wrappers.<UserInfo>lambdaQuery()
                .eq(UserInfo::getUserPhone, userInfo.getUserPhone());
        UserInfo checkPhone = this.getOne(userInfoLqw);
        if (ObjectUtil.isNotEmpty(checkPhone)) {
            throw new CheckException("手机号已注册,用户名为:【" + checkPhone.getUsername() + "】,请直接登录");
        }
        // 校验验证码
        this.checkSmsCode(userInfo.getUserPhone() + "reg", userInfo.getSmsPin());

        // 加密密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(userInfo.getPassword());

        UserInfo resUser = new UserInfo();
        resUser.setCreateName("unknow");
        resUser.setUserId(UUIDGenerator.getNextId(ID_PREFIX, this.getMaxNum()));
        resUser.setRoleId("RO20210800009");
        resUser.setUsername(userInfo.getUsername());
        resUser.setPassword(hashedPassword);
        resUser.setCreateTime(DateUtils.getNowTimeStamp());
        resUser.setUpdateTime(DateUtils.getNowTimeStamp());
        resUser.setCreateName("自行注册");
        resUser.setExpirationTime(null);
        resUser.setSmsPin(0);
        resUser.setGetSms(0);
        resUser.setExpirationTime(null);
        resUser.setUserPhone(userInfo.getUserPhone());
        baseMapper.insert(resUser);
        return baseMapper.selectById(resUser.getUserId());
    }

    /**
     * 获取验证码
     */
    @Override
    public Boolean getSmsCode(String userPhone, String type) throws Exception {
        // 验证手机号
        LambdaQueryWrapper<UserInfo> userInfoLqw = Wrappers.<UserInfo>lambdaQuery()
                .eq(UserInfo::getUserPhone, userPhone);
        UserInfo resUser = this.getOne(userInfoLqw);
        // 检测是否是已存在的用户
        if ("login".equals(type) && ObjectUtil.isEmpty(resUser)) {
            if (ObjectUtil.isEmpty(resUser)) {
                throw new CheckException("手机号未注册，请先去注册！");
            }
        }
        if ("reg".equals(type)) {
            if (ObjectUtil.isNotEmpty(resUser)) {
                throw new CheckException("手机号已被注册，请直接去登陆！");
            }
        }

//        if (ObjectUtil.isEmpty(userInfo)) {
//            userInfo = new UserInfo();
//            userInfo.setUsername(userPhone);
//            userInfo.setCreateTime(DateUtils.getNowTimeStamp());
//            userInfo.setCreateName("unknow");
//            userInfo.setUserId(UUIDGenerator.getNextId(ID_PREFIX, this.getMaxNum()));
//            userInfo.setRoleId("RO20210800009");
//            userInfo.setUserPhone(userPhone);
//        }
        // 生成验证码
        Integer code = RandomUtil.randomInt(100000, 999999);
//        redisUtils.set("code" + userPhone + type, String.valueOf(code));
//        // 设置过期时间为5分钟
//        redisUtils.expire("code" + userPhone + type, 300L);

//        userInfo.setSmsPin(code);
//        // 获取验证码的过期时间
//        LocalDateTime now = LocalDateTime.now();
//        userInfo.setExpirationTime(now.plusMinutes(5L).toInstant(ZoneOffset.of("+8")).toEpochMilli());
//        // 错误次数
//        int smsErr = ObjectUtil.isNotEmpty(userInfo.getSmsErr()) ? userInfo.getSmsErr() + 1 : 0;
//        userInfo.setSmsErr(smsErr);
//        // 设置验证码次数
//        int getSms = ObjectUtil.isNotEmpty(userInfo.getGetSms()) ? userInfo.getGetSms() + 1 : 1;
//        if (getSms > 3) {
//            throw new CheckException("验证码已超过今日最大获取次数！");
//        }
//        userInfo.setGetSms(getSms);
//        // 调用阿里云发送验证码
        sendSMS.send(Long.valueOf(userPhone), code);
//
//        return this.saveOrUpdate(userInfo);
        return true;
    }

//    static {
//        jedis = new Jedis("110.42.187.111", 10086);
//        jedis.auth("Cyfs8as2299o");//权限认证
//        jedis.select(1);
//    }

    @Override
    public UserInfo loginByPhone(UserInfo admin) {
        // 验证手机号
        LambdaQueryWrapper<UserInfo> userInfoLqw = Wrappers.<UserInfo>lambdaQuery()
                .eq(UserInfo::getUserPhone, admin.getUserPhone());
        UserInfo resUser = this.getOne(userInfoLqw);
        if (ObjectUtil.isEmpty(resUser)) {
            throw new CheckException("手机号未注册，请先去注册！");
        }
        // 校验验证码
        this.checkSmsCode(admin.getUserPhone() + "login", admin.getSmsPin());

        final UserInfo userDetails = this.loadUserByUsername(resUser.getUsername());

        final String token = jwtTokenProvider.generateToken(userDetails.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        userDetails.setToken(token);
        userDetails.setPassword(null);
//        admin.setRoles(new HashSet<Role>() {{
//            add(new Role("Super Admin", "super"));
//        }});

        return userDetails;
    }

    @Override
    public int getMaxNum() {
        return baseMapper.getUserMaxNum();
    }

    @Override
    public IPage<UserInfo> getAccountList(Map<String, Object> params, UserInfo vo) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<UserInfo>()
                .like(StringUtils.isNotBlank(vo.getUsername()), "u.username", vo.getUsername())
                .eq(StringUtils.isNotBlank(vo.getUserPhone()), "u.user_phone", vo.getUserPhone())
                .eq(StringUtils.isNotBlank(vo.getRoleId()), "u.role_id", vo.getRoleId())
                .eq("u.is_delete", Constant.NO_DELETE);
        return baseMapper.getAccountList(new Query<UserInfo>().getPage(params), queryWrapper);
    }

    @Override
    public Boolean checkUserRoleUse(String roleId) {
        int res = baseMapper.checkUserRoleUse(roleId);
        return res > 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean addOrUpdate(UserInfo userInfo) {

        if (StringUtils.isNotBlank(userInfo.getUserId())) {
            LambdaQueryWrapper<UserInfo> lqw = Wrappers.<UserInfo>lambdaQuery()
                    .eq(UserInfo::getUsername, userInfo.getUsername())
                    .ne(UserInfo::getUserId, userInfo.getUserId())
                    .eq(UserInfo::getIsDelete, userInfo.getIsDelete());
            UserInfo oldUserInfo = baseMapper.selectOne(lqw);
            if (null != oldUserInfo) {
                throw new CheckException(String.format("用户名【%s】重复，请重新设置！", userInfo.getUsername()));
            }
            // 更新代码
            return this.updateById(userInfo);
        } else {
            // 新增代码
            if (StringUtils.isNotBlank(userInfo.getPassword())) {
                userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
            } else {
                userInfo.setPassword(new BCryptPasswordEncoder().encode("123456"));
            }
            userInfo.setUserId(UUIDGenerator.getNextId(UserInfo.USER_PREFIX, this.getMaxNum()));
            userInfo.setCreateName(UserContextHolder.getUserInfo().getCreateName());
            userInfo.setCreateTime(DateUtils.getNowTimeStamp());
            return this.save(userInfo);
        }
    }

    @Override
    public Boolean delUserById(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            return this.removeById(userId);
        } else {
            throw new CheckException("用户ID参数为空！");
        }
    }

    @Override
    public Boolean updateEnableSetting(UserInfo user) {
        return SqlHelper.retBool(baseMapper.updateEnableSetting(user.getUserId(), user.getUxEnabled()));
    }

    @Override
    public String getDingTalkUid(String phone) throws ApiException {
        if (StringUtils.equalsIgnoreCase("000", phone)) {
            phone = UserContextHolder.getUserInfo().getUserPhone();
        }
        return dingTalkService.getUidByPhone(phone, true);
    }

    @Override
    public Boolean setDingTalkUid(UserInfo userInfo) {
        if (StringUtils.isNotBlank(userInfo.getDingtalkUid())) {
            userInfo.setUserId(UserContextHolder.getUserId());
            baseMapper.setDingTalkUid(userInfo);
        }
        return true;
    }

    /**
     * 校验验证码
     *
     * @param reqCode 前端传过来的验证码
     */
    private void checkSmsCode(String userPhone, Integer reqCode) {
//        Object code = redisUtils.get("code" + userPhone);
//        if (ObjectUtil.isEmpty(code)) {
//            throw new CheckException("验证码已过期，请重新获取验证码！");
//        }
//        if (!(reqCode + "").equals(code)) {
//            throw new CheckException("验证码验证错误！");
//        }
    }

    @Override
    public boolean save(UserInfo entity) {
        return super.save(entity);
    }

}
