package com.house.service;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.house.exception.CheckException;
import com.house.common.properties.DingtalkProperties;
import com.taobao.api.ApiException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DingTalkService {
    @Resource
    private DingtalkProperties aliYunConfig;

    private ConcurrentHashMap<String, String> tokenMap = new ConcurrentHashMap<>();

//    {"errcode":0,"access_token":"bfc1e8b839e93129a2dbb2e117cec65a","errmsg":"ok","expires_in":7200}

    public static void main(String[] args) throws ApiException {
//        getAdminList();
//        getToken();
//        sendWork();
//        getUserInfoById();
//        getToken();

    }

    public String getUidByPhone(String phone, boolean err) throws ApiException {
        String access_token = this.getToken();
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/getbymobile");
        OapiV2UserGetbymobileRequest req = new OapiV2UserGetbymobileRequest();
        req.setMobile(phone);
        OapiV2UserGetbymobileResponse rsp = client.execute(req, access_token);
        JSONObject jsonObject = JSONObject.parseObject(rsp.getBody());
        String resCode = String.valueOf(jsonObject.get("errcode"));
        if("0".equals(resCode)){
            return String.valueOf(jsonObject.getJSONObject("result").get("userid"));
        }else {
            if(err){
                throw new CheckException(String.valueOf(jsonObject.get("errmsg")));
            }else {
                return "";
            }
        }
    }


    /**
     * 获取token
     */
    public synchronized String getToken() throws ApiException {
        if (ObjectUtil.isNotEmpty(tokenMap.get("access_token"))
                && System.currentTimeMillis() <= Long.parseLong(tokenMap.get("expires_time"))) {
            return tokenMap.get("access_token");
        } else {
            return setAccessToken();
        }
    }

    private String setAccessToken() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(aliYunConfig.getAppKey());
        request.setAppsecret(aliYunConfig.getAppSecret());
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        tokenMap.put("access_token", response.getAccessToken());
        Long expiresTime = System.currentTimeMillis()+response.getExpiresIn() - 100;
        tokenMap.put("expires_time", String.valueOf(expiresTime));
        return response.getAccessToken();
    }

    public OapiMessageCorpconversationAsyncsendV2Response sendWork(String userId, String content) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setAgentId(1247279152L);
        request.setUseridList(userId);
        request.setToAllUser(false);

        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent(content);
        request.setMsg(msg);

//        msg.setMsgtype("image");
//        msg.setImage(new OapiMessageCorpconversationAsyncsendV2Request.Image());
//        msg.getImage().setMediaId("@lADOdvRYes0CbM0CbA");
//        request.setMsg(msg);
//
//        msg.setMsgtype("file");
//        msg.setFile(new OapiMessageCorpconversationAsyncsendV2Request.File());
//        msg.getFile().setMediaId("@lADOdvRYes0CbM0CbA");
//        request.setMsg(msg);
//
//        msg.setMsgtype("link");
//        msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
//        msg.getLink().setTitle("test");
//        msg.getLink().setText("test");
//        msg.getLink().setMessageUrl("test");
//        msg.getLink().setPicUrl("test");
//        request.setMsg(msg);
//
//        msg.setMsgtype("markdown");
//        msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
//        msg.getMarkdown().setText("##### text");
//        msg.getMarkdown().setTitle("### Title");
//        request.setMsg(msg);
//
//        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
//        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
//        msg.getOa().getHead().setText("head");
//        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
//        msg.getOa().getBody().setContent("xxx");
//        msg.setMsgtype("oa");
//        request.setMsg(msg);
//
//        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
//        msg.getActionCard().setTitle("xxx123411111");
//        msg.getActionCard().setMarkdown("### 测试123111");
//        msg.getActionCard().setSingleTitle("测试测试");
//        msg.getActionCard().setSingleUrl("https://www.dingtalk.com");
//        msg.setMsgtype("action_card");
//        request.setMsg(msg);
        return client.execute(request, getToken());
    }

    public static void getAllUser() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/queryonjob");
        OapiSmartworkHrmEmployeeQueryonjobRequest req = new OapiSmartworkHrmEmployeeQueryonjobRequest();
        req.setStatusList("2,3,5,-1");
        req.setOffset(0L);
        req.setSize(50L);
        OapiSmartworkHrmEmployeeQueryonjobResponse rsp = client.execute(req, "bfc1e8b839e93129a2dbb2e117cec65a");
        System.out.println(rsp.getBody());
    }

    public static void getAdminList() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/user/listadmin");
        OapiUserListadminRequest req = new OapiUserListadminRequest();
        OapiUserListadminResponse rsp = client.execute(req, "bfc1e8b839e93129a2dbb2e117cec65a");
        System.out.println(rsp.getBody());
    }

    public static void getUserInfoById() throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/v2/user/get");
        OapiV2UserGetRequest req = new OapiV2UserGetRequest();
        req.setUserid("182019081523650641");
        req.setLanguage("zh_CN");
        OapiV2UserGetResponse rsp = client.execute(req, "bfc1e8b839e93129a2dbb2e117cec65a");
        System.out.println(rsp.getBody());
    }

}
