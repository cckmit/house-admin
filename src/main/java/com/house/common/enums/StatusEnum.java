package com.house.common.enums;

public enum StatusEnum {
    LEASE_STATUS_ON(0, "租约正常"),
    LEASE_STATUS_OFF(1, "解约状态"),
    // 缴纳状态 0-未交 1-已交
    RENT_COST_STATUS_NO(0, "未交"),
    RENT_COST_STATUS_OK(1, "已交"),
    RENT_COST_STATUS_CANCEL(2, "解约"),
    // 房产状态
    HOUSE_STATUS_ON(1, "房屋正常"),
    HOUSE_STATUS_OFF(2, "房屋已退租"),
    // 数据字典
    COMMUNITY(1, "小区");

    private int key;
    private String value;

    public int getCode() {
        return key;
    }

    public String getValue() {
        return value;
    }

    StatusEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }


}
