package com.mpf.study.config.swagger.resource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * com.egoo.eeip.portal.server.config.swagger
 *
 * @author zhang xin
 * @date 2020/4/21
 */
@Component
@ConfigurationProperties(prefix = "properties.swagger.resource")
@Data
public class SwaggerResourceConfig {

    private List<SwaggerInfo> list;

    public SwaggerResourceConfig() {
        this.list = new ArrayList<>();
    }

}
