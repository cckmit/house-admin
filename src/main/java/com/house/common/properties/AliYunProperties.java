package com.house.common.properties;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Schema(name = "阿里云配置")
@Data
@ConfigurationProperties(
        prefix = "aliyun",
        ignoreUnknownFields = false
)
public class AliYunProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String endpoint;

    private String signName;

    private String templateCode;

}
