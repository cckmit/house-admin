package com.house.utils;

import com.house.model.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextHolder {

    public static String getUserId(){
     return getUserInfo().getUserId();
    }

    public static UserInfo getUserInfo(){
      return   (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
