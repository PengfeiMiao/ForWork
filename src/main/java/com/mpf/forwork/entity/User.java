package com.mpf.forwork.entity;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/5/31 14:51
 */
@Data
@Component
@Scope(ConfigurableListableBeanFactory.SCOPE_SINGLETON)
public class User {
    private Long id;
    private String name;
    private Integer age;
    private String email;

    public User(){}

    public User(Long id, String name){
        this.id = id;
        this.name = name;
    }

    /**
     * 定义一个bean对象
     * @return
     */
    @Bean(value="user0",initMethod="initUser",destroyMethod="destroyUser")
    public User getUser(){
        System.out.println("创建user实例");
        return new User(1L,"MPF");
    }

    public void initUser(){
        System.out.println("初始化用户bean之前执行");
    }
    public void destroyUser(){
        System.out.println("bean销毁之后执行");
    }
}
