package com.house.controller;


import com.house.common.bean.ResultBean;
import com.house.model.ReqUserInfo;
import com.house.model.UserInfo;
import com.house.service.impl.UserServiceImpl;
import com.house.utils.JwtTokenUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Tag(name = "登陆接口")
@RestController
@RequestMapping("/auth")
public class LoginController {

    private UserServiceImpl userDetailsService;
    private JwtTokenUtils jwtTokenProvider;
    private AuthenticationManager authenticationManager;

    @Autowired
    public void setUserDetailsService(UserServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setJwtTokenProvider(JwtTokenUtils jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Operation(summary = "后台用户登录")
    @PostMapping("/login")
    public ResultBean<UserInfo> adminLogin(@RequestBody UserInfo admin) {
        UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(admin.getUsername(), admin.getPassword());

        authenticationManager.authenticate(upaToken);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(admin.getUsername());
        final String token = jwtTokenProvider.generateToken(userDetails.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserInfo userInfo = userDetailsService.getCurrentUserInfo();
        userInfo.setToken(token);
        userInfo.setPassword(null);
        return new ResultBean<>(userInfo);
    }

    @Operation(summary = "手机号登陆")
    @PostMapping("/loginByPhone")
    public ResultBean<UserInfo> loginByPhone(@RequestBody UserInfo admin) {
        return ResultBean.ok(userDetailsService.loginByPhone(admin));
    }

    @PostMapping("wxlogin")
    @Operation(summary = "小程序用户登录")
    public ResultBean<Map<String, Object>> wxLogin(@RequestBody ReqUserInfo reqUserInfo) throws Exception {
        return new ResultBean<>(userDetailsService.wxLogin(reqUserInfo));
    }


//    @PostMapping("/wx/bindPhone")
//    @Operation(summary = value = "微信绑定电话",tags={"登陆接口"})
//    public ResultBean<> bindPhone(HttpServletRequest request, @RequestBody ReqGetPhoneNum reqGetPhoneNum)throws Exception{
//        PtpUserInfo ptpUserInfo=  getUserInfo(request);
//        String userPhone= WechatDecryptDataUtil.decryptData(reqGetPhoneNum.getEncryptedData(),ptpUserInfo.getSessionKey(),reqGetPhoneNum.getIv());
//        userPhone=JSONObject.parseObject(userPhone).getString("phoneNumber");
//        QueryWrapper<PtpUserInfo> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("wx_open_id",ptpUserInfo.getWxOpenId());
//        PtpUserInfo ptpUserInfos =ptpUserInfoService.getOne(queryWrapper);
//        ptpUserInfo.setUserPhone(userPhone);
//        ptpUserInfo.setUserInfoId(ptpUserInfos.getUserInfoId());
//        boolean result=ptpUserInfoService.updateById(ptpUserInfo);
//        if(result) {
//            String token = request.getHeader("Authorization").substring("Bearer ".length());
//            redisUtil.set(token, JSON.toJSONString(ptpUserInfo));
//            log.info("=======>"+JSON.toJSONString(ptpUserInfo));
//            return GenericResponse.response(ServiceError.NORMAL);
//        }else{
//            return GenericResponse.response(ServiceError.GLOBAL_ERR_BIND_PHONE);
//        }
//    }
//    @PostMapping("/wx/binUserName")
//    @Operation(summary = value = "微信绑定用户名称",tags={"登陆接口"})
//    public GenericResponse binUserName(HttpServletRequest request, @RequestBody ReqGetPhoneNum reqGetPhoneNum)throws Exception{
//        PtpUserInfo ptpUserInfo=  getUserInfo(request);
//        QueryWrapper<PtpUserInfo> queryWrapper=new QueryWrapper<>();
//        queryWrapper.eq("wx_open_id",ptpUserInfo.getWxOpenId());
//        ptpUserInfo =ptpUserInfoService.getOne(queryWrapper);
//        ptpUserInfo.setUserName(reqGetPhoneNum.getUserName());
//        boolean result=ptpUserInfoService.updateById(ptpUserInfo);
//        if(result) {
//            String token = request.getHeader("Authorization").substring("Bearer ".length());
//            redisUtil.set(token, JSON.toJSONString(ptpUserInfo));
//            return GenericResponse.response(ServiceError.NORMAL);
//        }else{
//            return GenericResponse.response(ServiceError.GLOBAL_ERR_BIND_PHONE);
//        }
//    }
//    @GetMapping("/getAllQRCode")
//    @Operation(summary = value = "获取所有设备的小程序码",tags={"登陆接口"})
//    public GenericResponse getAllQRCode(){
//        boolean result=true;
//        QueryWrapper queryWrapper =new QueryWrapper<>();
//        queryWrapper.isNull("recode_path");
//        List<PtpDevice> list= ptpDeviceService.list(queryWrapper);
//        for (PtpDevice ptpDevice: list) {
//            String imageName=ptpDevice.getDeviceBtName()+".png";
//            if(weChatService.createQRCode("deviceName="+ptpDevice.getDeviceBtName(),imageName)){
//                ptpDevice.setRecodePath("QRCode/" +imageName);
//                ptpDeviceService.updateById(ptpDevice);
//            }else{
//                result=false;
//                break;
//            }
//        }
//        if(result){
//            return GenericResponse.response(ServiceError.NORMAL,"创建小程序码成功");
//        }else{
//            return GenericResponse.response(ServiceError.GLOBAL_ERR_PTP_CREATE_QE_CODE);
//        }
//    }

    @GetMapping("getPermCode")
    public ResultBean<List<Integer>> getPermCode() {
        return ResultBean.ok(Stream.of(1000, 3000, 5000).collect(Collectors.toList()));
    }

    @Operation(summary = "获取验证码")
    @GetMapping("getCode/{type}")
    public ResultBean<Boolean> getCode(@RequestParam("userPhone") Long userPhone,
                                       @PathVariable("type") String type) throws Exception {
        return ResultBean.ok(userDetailsService.getSmsCode(String.valueOf(userPhone), type));
    }

    @PostMapping("register")
    public ResultBean<UserInfo> registerUser(@RequestBody UserInfo userInfo) {
        return ResultBean.ok(userDetailsService.registerUser(userInfo));
    }



}