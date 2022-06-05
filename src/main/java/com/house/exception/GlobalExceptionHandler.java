package com.house.exception;

import cn.hutool.core.util.ObjectUtil;
import com.house.common.enums.ResultCode;
import com.house.common.bean.ResultBean;
import com.qiniu.common.QiniuException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 处理空指针的异常
     */
    @ExceptionHandler(value = NullPointerException.class)
    public ResultBean<Object> exceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("发生空指针异常！位置:", e);
        return ResultBean.error(ResultCode.NULL_EXCEPTION);
    }

    @ExceptionHandler(value = QiniuException.class)
    public ResultBean<Object> exceptionHandler(QiniuException e) {
        log.error("七牛云文件上传失败:{}", e.getMessage());
        return ResultBean.error(ResultCode.QINIU_UPLOAD_NULL);
    }

    /**
     * 处理用户名密码错误
     */
    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResultBean<Object> commence(BadCredentialsException e) {
        log.error("BadCredentialsException:{}", e.getMessage());
        return ResultBean.error(ResultCode.USER_CREDENTIALS_ERROR);
    }

    @ExceptionHandler(value = {InternalAuthenticationServiceException.class})
    public ResultBean<Object> commence(InternalAuthenticationServiceException e) {
        log.error("InternalAuthenticationServiceException:{}", e.getMessage());
        return ResultBean.error(ResultCode.USER_CREDENTIALS_ERROR);
    }

    @ExceptionHandler(value = {ExpiredJwtException.class})
    public ResultBean<Object> commence(ExpiredJwtException e) {
        log.error("token过期:{}", e.getMessage());
        return ResultBean.error(ResultCode.PERMISSION_NO_ACCESS);
    }

    /*
     * desc: 处理自定义异常
     * date 11:04 2021/6/23
     * @author cuifuan@aliyun.com
     **/
    @ExceptionHandler(value = CheckException.class)
    public ResultBean<Object> exceptionHandler(CheckException e) {
        if (ObjectUtil.isEmpty(e.getResultCode())) {
            return ResultBean.error(e.getMessage());
        } else {
            return ResultBean.error(e.getResultCode());
        }

    }

    @ExceptionHandler(value = Exception.class)
    public ResultBean<Object> exceptionHandler(HttpServletRequest req, Exception e) {
        log.error("Exception.class===>{}",e.getMessage());
        if(ObjectUtil.isNotEmpty(e.getLocalizedMessage())){
            return ResultBean.error(e.getLocalizedMessage());
        }else {
            return ResultBean.error(e.getMessage());
        }
    }
}
