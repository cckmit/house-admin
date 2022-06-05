package com.house.exception;

import com.house.common.enums.ResultCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CheckException extends BaseException {

    /**
     * 自定义异常枚举类
     */
    private ResultCode resultCode;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    public CheckException(ResultCode resultCode) {
        super("{code:" + resultCode.code() + ",msg:" + resultCode.message() + "}");
        this.resultCode = resultCode;
        this.code = resultCode.code();
        this.msg = resultCode.message();
    }

    public CheckException(String message) {
        super(message);
    }
}
