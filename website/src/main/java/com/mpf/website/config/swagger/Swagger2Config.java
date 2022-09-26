package com.mpf.website.config.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 接口文档配置
 *
 * @author jie liu
 * @date 2018/7/8
 */
@Configuration
@EnableOpenApi
@ConditionalOnProperty(prefix = "properties.swagger", name = "enabled", havingValue = "true")
public class Swagger2Config {
    @Bean
    public Docket serverApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(serverApiInfo())
                .groupName("For Work API")
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mpf.website.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    @Bean
    public ApiInfo serverApiInfo() {
        return new ApiInfoBuilder()
                .title("For Work API")
                .version("1.0")
                .build();
    }
}
