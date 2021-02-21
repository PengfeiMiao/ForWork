package com.mpf.study.staticobject;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/3/17 15:49
 */
public class CommonStatic {

    public static final String NM_SPACE = "forWork";

    public static final String TOPIC = "sendMessage";

    public static final ThreadLocal<Object> threadLocal = new ThreadLocal<Object>() {
        /**
         * ThreadLocal没有被当前线程赋值时或当前线程刚调用remove方法后调用get方法，返回此方法值
         */
        @Override
        protected Object initialValue() {
            return null;
        }
    };

    public static volatile String MESSAGE = "";

    public static ConcurrentHashMap<String, ScheduledFuture<?>> rateLimiterMap = new ConcurrentHashMap<>();
}
