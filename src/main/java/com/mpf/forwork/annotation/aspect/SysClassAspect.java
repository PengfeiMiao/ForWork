package com.mpf.forwork.annotation.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Author: MiaoPengfei
 * @Date: 2021/1/11 15:21
 * @Description:
 */
@Component
@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE + 1)
@Slf4j
public class SysClassAspect {
    @Pointcut("@within(com.mpf.forwork.annotation.SysClass)")
    public void test() {
    }

    @Pointcut("@annotation(com.mpf.forwork.annotation.SysClass)")
    public void test1() {
    }

    @Pointcut("test() || test1()")
    public void test2() {
    }

    @Before("test2()")
    public void before() {
        log.info("SysClass annotation effect");
    }
}
