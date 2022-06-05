package com.house.common.bean;

import com.house.utils.UserContextHolder;
import lombok.Data;

@Data
public class BaseVO {

    private int pageNo = 1;

    private int pageSize = 10;

    private String createUser;

    private String updateUser;

    private long createTime;

    private long updateTime;

    private int isDelete = 0;

    private String userId = UserContextHolder.getUserId();

}