package com.mpf.forwork.annotation.aspect;

import com.google.gson.Gson;
import com.mpf.forwork.messageobj.Message;
import com.mpf.forwork.service.kafka.KafkaProducer;
import com.mpf.forwork.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @Author: MiaoPengfei
 * @Description: 本切面通过两种方式实现拦截及消息推送，
 * 经测试两种拦截不能作用于同一目标方法，优先生效的是切入点指定具体方法的方式
 * @Date: Create in 10:10 2020/9/15
 */
@Slf4j
@Component
@Aspect
public class SysMsgAspect {

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private SpringContextUtil springContextUtil;

    //方法一：指定注解拦截
    @Pointcut("@annotation(com.mpf.forwork.annotation.SysMsg)")
    public void msg() {
    }

    //方法二：指定方法拦截
    @Pointcut("execution(* com.mpf.forwork.controller.MessageController.test(..))")
    private void test() {
    }

    @Before("msg()")
    public void before() {
        log.info("SysMsg annotation begin");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        if (request == null) {
            return;
        }
        log.info("ContextPath: {}", request.getContextPath());
        log.info("RequestURI: {}", request.getRequestURI());
        log.info("Class: {}", request.getClass());
        log.info("Method: {}", request.getMethod());
        log.info("PathInfo: {}", request.getPathInfo());
        log.info("ServletPath: {}", request.getServletPath());
        Enumeration<String> enums = request.getParameterNames();
        List<String> list = new ArrayList<>();
        while (enums.hasMoreElements()) {
            String name = enums.nextElement();
            log.info(name+": "+request.getParameter(name));
            list.add(request.getParameter(name));
        }
        //执行方法
        try {
            Long start = System.currentTimeMillis();

            Class clazz = springContextUtil.getType("messageController");
            Method method = clazz.getMethod("test2", String.class, String.class);
            method.invoke(clazz.newInstance(), list.get(0), list.get(1));
            //执行方法后
            System.out.println("执行了：" + (System.currentTimeMillis() - start) + "s");
            if ("/message/test2".equals(request.getRequestURI()) && "1".equals(list.get(0))) {
                producer.send(new Message("message is " + list.get(1)));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Around("test()")
    public Object testAround(ProceedingJoinPoint pjp) throws Throwable {
        //获取参数
        Object[] args = pjp.getArgs();
        System.out.println("参数args:" + args[0]);
        //执行方法前
        Object returnVal = null;
        //执行方法
        try {
            Long start = System.currentTimeMillis();
            returnVal = pjp.proceed();
            /**
             * 另一种通用方法
             * 通过反射执行目标方法
             */
//            Class clazz = springContextUtil.getType("messageController");
//            Method method = clazz.getMethod("test", String.class, String.class);
//            returnVal = method.invoke(clazz.newInstance(), args[0].toString(), args[1].toString());

            //执行方法后
            System.out.println("执行了：" + (System.currentTimeMillis() - start) + "s");
            if ("1".equals(args[0].toString())) {
                producer.send(new Message("message is " + args[1]));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return returnVal;
    }

}
