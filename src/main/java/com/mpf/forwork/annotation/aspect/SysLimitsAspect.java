package com.mpf.forwork.annotation.aspect;

import com.mpf.forwork.util.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Aspect
public class SysLimitsAspect {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtil redisUtil;

    //用来存放不同接口的RateLimiter(key为接口名称，value为RateLimiter)
    //private ConcurrentHashMap<String, RedisUtil> map = new ConcurrentHashMap<>();

    /**
     * 基于自定义注解
     * //@annotation(com.mpf.forwork.annotation.SysLimits)
     * 基于包路径
     */
    @Pointcut("execution(* com.mpf.forwork.controller..*.*(..))")
    public void limit() {
    }

    @Around("limit()")
    public Object around(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        Object obj = null;
        //获取拦截的方法名
        Signature sig = joinPoint.getSignature();
        //获取拦截的方法名
        MethodSignature msig = (MethodSignature) sig;
        //注解所在方法名 区分不同的限流策略
        String functionName = msig.getName();

        try {
            //限流器选择 rateLimiter / rateLimiter2
            if(redisUtil.rateLimiter2(functionName, 5)) {
                //执行方法
                log.info("接受了请求：" + functionName);
                obj = joinPoint.proceed();
            } else {
                //拒绝了请求（服务降级）
                log.info("拒绝了请求：" + functionName);
                outErrorResult("系统繁忙!");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return obj;
    }

    //将结果返回
    public void outErrorResult(String result) {
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        if (response == null) {
            return;
        }
        response.setContentType("application/json;charset=UTF-8");
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(result.getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}