package com.mpf.forwork.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Author: MiaoPengfei
 * @Description:
 * @Date: Create in 14:01 2020/9/15
 */
@Slf4j
@Component
public class SpringContextUtil implements ApplicationContextAware{

    // Spring应用上下文环境
    public static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的回调方法。设置上下文环境
     *
     * @param applicationContext
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     *
     * @param name
     * @return
     */
    public Class getType(String name) {
        return applicationContext.getType(name);
    }

    /**
     * 判断是否存在定义的对象
     *
     * @param name
     * @return
     */
    public boolean containsBean(String name)  {
        return applicationContext.containsBean(name);
    }
    /**
     * 获取对象
     *
     * @param name
     * @return Object
     * @throws BeansException
     */
    public Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }
}