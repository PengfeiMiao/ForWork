package com.mpf.website.config.swagger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Optional;
import com.mpf.website.config.swagger.resource.SwaggerInfo;
import com.mpf.website.config.swagger.resource.SwaggerResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.ClassSupport;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.*;

/**
 * 自定义Swagger Resources Provider
 *
 * @author egoo
 * @date 2020/4/16
 */

@Primary
@Component
public class CustomSwagger implements SwaggerResourcesProvider {
    private final String swagger1Url;
    private final String swagger2Url;
    @VisibleForTesting
    boolean swagger1Available;
    @VisibleForTesting
    boolean swagger2Available;
    private final DocumentationCache documentationCache;

    @Autowired
    private SwaggerResourceConfig swaggerResource;

    public CustomSwagger(Environment environment, DocumentationCache documentationCache) {
        this.swagger1Url = environment.getProperty("springfox.documentation.swagger.v1.path", "/api-docs");
        this.swagger2Url = environment.getProperty("springfox.documentation.swagger.v2.path", "/v2/api-docs");
        this.swagger1Available = ClassSupport.classByName("springfox.documentation.swagger1.web.Swagger1Controller").isPresent();
        this.swagger2Available = ClassSupport.classByName("springfox.documentation.swagger2.web.Swagger2Controller").isPresent();
        this.documentationCache = documentationCache;
    }

    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList();
        Iterator var2 = this.documentationCache.all().entrySet().iterator();

        while (var2.hasNext()) {
            Map.Entry<String, Documentation> entry = (Map.Entry) var2.next();
            String swaggerGroup = entry.getKey();
            SwaggerResource swaggerResource;
            if (this.swagger1Available) {
                swaggerResource = this.resource(swaggerGroup, this.swagger1Url);
                swaggerResource.setSwaggerVersion("1.2");
                resources.add(swaggerResource);
            }

            if (this.swagger2Available) {
                swaggerResource = this.resource(swaggerGroup, this.swagger2Url);
                swaggerResource.setSwaggerVersion("2.0");
                resources.add(swaggerResource);
            }
        }

        // 静态地址
        List<SwaggerInfo> swaggerList = swaggerResource.getList();
        swaggerList.forEach(e -> {
            SwaggerResource customSwagger = this.resource(e.getName(), e.getUrl());
            customSwagger.setSwaggerVersion("2.0");
            resources.add(customSwagger);
        });
        Collections.sort(resources);

        return resources;
    }

    private SwaggerResource resource(String swaggerGroup, String baseUrl) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(swaggerGroup);
        swaggerResource.setUrl(this.swaggerLocation(baseUrl, swaggerGroup));
        return swaggerResource;
    }

    private String swaggerLocation(String swaggerUrl, String swaggerGroup) {
        String base = (String) Optional.of(swaggerUrl).get();
        return "default".equals(swaggerGroup) ? base : base + "?group=" + swaggerGroup;
    }
}
