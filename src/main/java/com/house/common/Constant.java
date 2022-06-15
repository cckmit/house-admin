package com.house.common;

public class Constant {

    private Constant() {
        throw new IllegalStateException("常量类");
    }

    /**
     * 房东
     */
    public static final int PERSON_TYPE_LANDLARD = 1;
    /**
     * 租客
     */
    public static final int PERSON_TYPE_TENANT = 2;
    /**
     * 登录凭证校验。通过 wx.login 接口获得临时登录凭证 code 后
     * 传到开发者服务器调用此接口完成登录流程。更多使用方法详见 小程序登录。
     */
    public static final String AUTH_CODE2_SESSION = "https://api.weixin.qq.com/sns/jscode2session";

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
    public static final int IS_DELETED = 1;
    public static final String DASHBOAD_ID = "1";
}
