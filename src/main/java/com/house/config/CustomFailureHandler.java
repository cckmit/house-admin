package com.house.config;

import com.house.common.enums.ResultCode;
import com.house.common.bean.ResultBean;
import com.house.utils.ResUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
        ResultBean<Object> result = new ResultBean<>();
        if (e instanceof BadCredentialsException) {
            result.setResultCode(ResultCode.USER_CREDENTIALS_ERROR);
        } else if (e instanceof LockedException) {
            result.setResultCode(ResultCode.USER_ACCOUNT_LOCKED);
        } else if (e instanceof DisabledException) {
            result.setResultCode(ResultCode.USER_ACCOUNT_DISABLE);
        } else if (e instanceof CredentialsExpiredException) {
            result.setResultCode(ResultCode.USER_CREDENTIALS_EXPIRED);
        } else if (e instanceof AccountExpiredException) {
            result.setResultCode(ResultCode.USER_ACCOUNT_EXPIRED);
        } else if (e instanceof InternalAuthenticationServiceException) {
            result.setMsg(e.getCause().getMessage());
        }

        ResUtils.resJson(resp, null, result);
    }
}
