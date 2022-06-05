package com.house.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(
        prefix = "oss.qiniu",
        ignoreUnknownFields = false
)
public class QiniuProperties {

    private String accessKey;

    private String bucket;

    private String secretKey;

    private String domain;
}
