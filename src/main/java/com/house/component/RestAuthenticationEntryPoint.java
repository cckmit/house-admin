package com.house.component;

import com.house.common.bean.ResultBean;
import com.house.common.enums.ResultCode;
import com.house.utils.ResUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * desc:当未登录或者token失效访问接口时，自定义的返回结果
 * date 2022/4/18 18:35
 *
 * @author cuifuan
 **/
@Slf4j
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ResUtils.resJson(response, null, ResultBean.error(ResultCode.PERMISSION_NO_ACCESS));
    }
}
