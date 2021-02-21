package com.mpf.study.config;

import com.mpf.study.entity.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * 应用配置类
 *
 * @author LogicArk
 * @date 2019/5/10
 */
@Data
@SpringBootConfiguration
public class ApplicationConfig {

    private static String serverPortOfApp;

    /**
     * 服务端口
     */
    @Value("${server.port}")
    private String serverPort;

    public static String getServerPortOfApp() {
        return serverPortOfApp;
    }

    @PostConstruct
    public void setParamsToStaticFileds() {
        serverPortOfApp = this.getServerPort();
    }

    @Bean
    public User getUser() {
        User user = new User();
        user.setUsername("TEST");
        return user;
    }
}
