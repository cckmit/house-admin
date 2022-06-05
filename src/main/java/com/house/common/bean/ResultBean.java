package com.house.common.bean;

import com.house.common.enums.ResultCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Schema(name = "统一返回处理")
@Data
@NoArgsConstructor
public class ResultBean<T> implements Serializable {

    public static final int SUCCESS = 0;
    public static final int FAIL = -1;
    private static final long serialVersionUID = 1L;
    private String msg = "success";

    private int code = SUCCESS;

    private T data;

    public ResultBean(T data) {
        super();
        this.data = data;
    }

    public ResultBean(Throwable e) {
        super();
        this.msg = e.toString();
        this.code = FAIL;
    }

    public static <T> ResultBean<T> ok(T data) {
        return new ResultBean<>(data);
    }

    public static ResultBean<Object> error(ResultCode resultCode) {
        ResultBean<Object> result = new ResultBean<>();
        result.setCode(resultCode.code());
        result.setMsg(resultCode.message());
        return result;
    }

    public static ResultBean<Object> error(String errorMsg) {
        ResultBean<Object> result = new ResultBean<>();
        result.setCode(ResultBean.FAIL);
        result.setMsg(errorMsg);
        return result;
    }

    public ResultBean<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public ResultBean<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public ResultBean<T> setData(T data) {
        this.data = data;
        return this;
    }

    public void setResultCode(ResultCode resultCode) {
        this.code = resultCode.code();
        this.msg = resultCode.message();
    }
}