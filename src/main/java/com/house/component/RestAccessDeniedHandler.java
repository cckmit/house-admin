package com.house.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.house.common.bean.ResultBean;
import com.house.common.enums.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * desc: 当访问接口没有权限时，自定义的返回结果
 * date 2022/4/18 16:22
 *
 * @author cuifuan
 **/
@Slf4j
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        log.error("=======访问接口没有权限=======\n", e);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(200);
        ResultBean<String> resultBean = new ResultBean<>();
        resultBean.setResultCode(ResultCode.PERMISSION_NO_ACCESS);
        response.getWriter().println(new ObjectMapper().writeValueAsString(resultBean));
        response.getWriter().flush();
        response.getWriter().close();
    }
}
