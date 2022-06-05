package com.house.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Schema(name = "登录用户信息")
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("user")
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo extends BaseBean implements Serializable, UserDetails {

    @TableField(exist = false)
    public final static String USER_PREFIX = "U";
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;
    private String username;
    private String password;
    private String userPhone;
    private Long createTime;
    private Long updateTime;
    private String token;
    private String dingtalkUid;
    @TableField(exist = false)
    private String roleName;
    private String roleId;
    private Boolean uxEnabled;
    @Schema(name = "微信OpenId")
    private String wxOpenId;
    @Schema(name = "短信验证码")
    private Integer smsPin;
    @Schema(name = "短信验证码过期时间")
    private Long expirationTime;
    @Schema(name = "尝试验证短信验证码次数")
    private Integer smsErr;
    @Schema(name = "获取短信验证码次数")
    private Integer getSms;
    private String createName;
    private String updateName;
    @Schema(name = "备注")
    private String remark;
    @TableField(exist = false)
    private String sessionKey;

    @Schema(name = "头像")
    private String avatar;

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    /*账号是否没过期*/
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /*账号是否没过期*/
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /*密码是否没过期*/
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /*锁定*/
    @Override
    public boolean isEnabled() {
        return this.getUxEnabled();
    }
}
