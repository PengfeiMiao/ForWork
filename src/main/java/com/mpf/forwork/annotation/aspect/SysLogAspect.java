package com.mpf.forwork.annotation.aspect;

import com.mpf.forwork.staticobject.CommonStatic;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: xy
 * @create: 2019-05-15
 */
@Component
@Aspect
public class SysLogAspect {

    
    @Pointcut("@annotation(com.mpf.forwork.annotation.SysLogs)")
    public void log() {
    }

    @Before("log()")
    public void before() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();
        if (request == null) {
            return;
        }

        ThreadContext.put("UserId", "userCode");
        ThreadContext.put("UserName","userName");
        ThreadContext.put("UserType", "userType");

        ThreadContext.put("Agent", request.getHeader("User-Agent"));
        ThreadContext.put("RemoteHost", request.getRemoteAddr());
        ThreadContext.put("NameSpace", CommonStatic.NM_SPACE);

    }


}
