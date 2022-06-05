package com.house.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Constant {

    public static int OUT_RENT = 0;
    public static int COLLECT_RENT = 1;

    /**
     * 房东
     */
    public final static int PERSON_TYPE_LANDLARD = 1;
    /**
     * 登录凭证校验。通过 wx.login 接口获得临时登录凭证 code 后传到开发者服务器调用此接口完成登录流程。更多使用方法详见 小程序登录。
     */
    public final static String AUTH_CODE2_SESSION = "https://api.weixin.qq.com/sns/jscode2session";
    /**
     * 租客
     */
    public final static int PERSON_TYPE_TENANT = 2;
    /**
     * 超管
     */
    public final static int USER_TYPE_ADMIN = 1;
    /**
     * 用户
     */
    public final static int USER_TYPE_EMP = 2;

    /** 数据权限过滤 */
    public static final String SQL_FILTER = "sql_filter";
    /**
     * 当前页码
     */
    public static final String PAGE = "pageNo";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "pageSize";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";

    public static final int NO_DELETE = 0;
    public static final int DELETED = 1;

    /**
     * 身份证正则
     */
    public static final String CARD_REG = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
            "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";

    public static final String DASHBOAD_ID = "1";
}
