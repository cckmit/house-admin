package com.house.utils;

import cn.hutool.core.date.format.FastDateFormat;
import cn.hutool.core.util.StrUtil;

import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class UUIDGenerator {

    private static final FastDateFormat pattern = FastDateFormat.getInstance("yyyyMM");

    public static String getNextId(String prefix, int maxNum) {
        StringBuilder builder = new StringBuilder(prefix);// 取系统当前时间作为订单号前半部分
        builder.append(pattern.format(new Date()));
        String pipeNum = StrUtil.fillBefore(String.valueOf(maxNum), '0', 5);
        builder.append(pipeNum);
        return builder.toString();
    }
}
