package com.house.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.house.common.bean.ResultBean;
import com.house.model.UserInfo;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResUtils {

    private ResUtils() {
        throw new IllegalStateException("工具类");
    }


    public static void resJson(HttpServletResponse resp, Authentication auth, ResultBean<Object> resultBean) throws IOException {
        resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = resp.getWriter();

        if (null != auth) {
            UserInfo admin = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            admin.setPassword(null);
            resultBean.setData(admin);
        }

        //登录成功后返回json 前端根据返回的信息进行页面跳转
        writer.write(new ObjectMapper().writeValueAsString(resultBean));
        writer.flush();
        writer.close();
    }

}
