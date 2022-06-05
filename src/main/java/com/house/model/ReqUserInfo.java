package com.house.model;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "请求用户信息参数")
public class ReqUserInfo {
    @Schema(name = "微信编号")
    String wxCode;
    @Schema(name = "用户信息的加密数据")
    String encryptedData;
    @Schema(name = "加密算法的初始向量")
    String iv;
    @Schema(name = "手机号码")
    String userPhone;
    @Schema(name = "登录密码")
    String userPwd;
    @Schema(name = "手机验证码")
    String validateCode;
}