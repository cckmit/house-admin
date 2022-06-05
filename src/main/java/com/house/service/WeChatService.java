package com.house.service;

/**
 * 微信业务接口
 */
public interface WeChatService {

    /**
     * 小程序登录
     */
//    GenericResponse wxLogin(String code)throws Exception;


    String getAccessToken();

    boolean createQRCode(String scene,String fileName);

    String jcode2Session(String code) throws Exception;
}