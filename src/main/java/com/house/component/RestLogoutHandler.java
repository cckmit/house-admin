package com.house.component;

import com.house.common.bean.ResultBean;
import com.house.utils.ResUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class RestLogoutHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest resp, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        ResUtils.resJson(httpServletResponse, authentication, new ResultBean<>("登出成功！"));
    }
}
