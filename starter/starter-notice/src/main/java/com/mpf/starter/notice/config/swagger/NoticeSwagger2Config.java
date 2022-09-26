package com.mpf.starter.notice.config.swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


/**
 *  接口文档配置
 *
 *  @author jie liu
 *  @date 2018/7/8
 */
@Configuration
@EnableOpenApi
@Import(BeanValidatorPluginsConfiguration.class)
@ConditionalOnProperty(prefix = "properties.swagger", name = "enabled", havingValue = "true")
public class NoticeSwagger2Config {

	@Bean
	public Docket noticeMessageSwaggerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(noticeMessageSwaggerApiInfo())
				.groupName("WebSocket消息通知接口")
				.enable(true)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.mpf.website.controller"))
				.paths(PathSelectors.any())
				.build();
	}

	@Bean
	public ApiInfo noticeMessageSwaggerApiInfo() {
		return new ApiInfoBuilder()
				.title("WebSocket消息通知接口")
				.version("1.0")
				.build();
	}
}
