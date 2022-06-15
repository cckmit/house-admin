package com.house;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan("com.house.common.properties")
//@EnableTransactionManagement
public class HouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseApplication.class, args);
    }

//    @Bean
//    public ReloadableResourceBundleMessageSource messageSource() {
//        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
//        reloadableResourceBundleMessageSource.setBasename("classpath:zh_cn_messages");
//        reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
//        return reloadableResourceBundleMessageSource;
//    }

    //用注入的方式配置FastJson
//    @Bean
//    public HttpMessageConverters fastJsonMassageConverter() {
//        //创建FastJson的消息转换器
//        FastJsonHttpMessageConverter convert = new FastJsonHttpMessageConverter();
//        //创建FastJson的配置对象
//        FastJsonConfig config = new FastJsonConfig();
//        //对Json数据进行格式化
//        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
//
//        convert.setFastJsonConfig(config);
//        return new HttpMessageConverters(convert);
//    }

}