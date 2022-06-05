package com.house.exception;

import com.alibaba.fastjson.JSON;
import com.house.common.enums.ResultCode;

public class BaseException extends RuntimeException {
    public BaseException() {
        super();
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }

    public BaseException(ResultCode resultCode) {
        super(JSON.toJSONString(resultCode));
    }
}
