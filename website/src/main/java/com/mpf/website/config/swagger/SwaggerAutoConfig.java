package com.mpf.website.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.web.UiConfiguration;

/**
 * swagger自动配置
 *
 * @author egoo
 * @date 2020/1/29
 */
@Configuration
public class SwaggerAutoConfig {
	@Bean
	UiConfiguration uiConfig() {
		String[] supportedSubmitMethod = UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS;
		return new UiConfiguration(
				null,
				"list",
				"alpha",
				"schema",
				supportedSubmitMethod,
				false,
				true
		);
	}
}
