package com.house.model;

import lombok.Data;

@Data
public class Audience {
    private String clientId;
    private String base64Secret;
    private String name;
    private int expiresSecond;
}