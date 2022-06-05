package com.house.common.properties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(
        prefix = "dingtalk",
        ignoreUnknownFields = false
)
@NoArgsConstructor
public class DingtalkProperties {
    // https://open-dev.dingtalk.com/fe/app#/corp/app
    @Schema(name = "钉钉应用的agentId")
    private String agentId;

    private String appKey;

    private String appSecret;
}
