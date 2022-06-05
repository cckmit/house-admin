package com.house.service;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.aliyun.teaopenapi.models.Config;
import com.house.common.properties.AliYunProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Slf4j
@Service
public class SendSMS {

    @Resource
    private AliYunProperties aliYunConfig;

    public SendSmsResponseBody send(Long phone, Integer smsPin) throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(aliYunConfig.getAccessKeyId())
                // 您的AccessKey Secret
                .setAccessKeySecret(aliYunConfig.getAccessKeySecret());
        // 访问的域名
        config.endpoint = aliYunConfig.getEndpoint();
        com.aliyun.dysmsapi20170525.Client client = new Client(config);
        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setPhoneNumbers(String.valueOf(phone))
                .setSignName("崔富安")
                .setTemplateCode(aliYunConfig.getTemplateCode())
                .setTemplateParam("{\"code\":\"" + smsPin + "\"}");
        SendSmsResponse sendSmsResponse= client.sendSms(sendSmsRequest);
        // 复制代码运行请自行打印 API 的返回值
        log.info("阿里云短信发送日志:{}",sendSmsResponse);
        return sendSmsResponse.body;
    }

}
