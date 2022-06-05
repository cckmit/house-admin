package com.house.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //说明这是一个配置类
public class SwaggerConfiguration {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(this.getInfo())
                .externalDocs(this.getExternalDocumentation());
    }

    private ExternalDocumentation getExternalDocumentation() {
        return new ExternalDocumentation()
                .description("租赁系统API文档")
                .url("https://springshop.wiki.github.org/docs");
    }


    private Info getInfo() {
        return new Info().title("房屋租赁管理系统")
                .description("房屋租赁管理系统API接口信息")
                .contact(this.getContact())
                .version("v1.0")
                .license(this.getLicense());
    }

    private Contact getContact() {
        return new Contact()
                .name("cuifuan")
                .url("https://gitee.com/cuifuan")
                .email("eyck.cui@foxmail.com");
    }

    private License getLicense() {
        return new License()
                .name("Apache 2.0")
                .url("https://springdoc.org");
    }

}
