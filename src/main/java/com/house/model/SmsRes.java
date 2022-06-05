package com.house.model;

import lombok.Data;

@Data
public class SmsRes {

    private String bizId;

    private String code;

    private String message;

    private String requestId;

}
