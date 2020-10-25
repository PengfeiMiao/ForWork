package com.mpf.forwork.service.schedule;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/7/25 19:23
 */

import com.mpf.forwork.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

import static com.mpf.forwork.staticobject.CommonStatic.rateLimiterMap;

/**
 * 用来实现对限流接口的UUID生成
 * 若接口数量大，起的线程过多会占用过多内存
 * 改进思路：用一个ConcurrentHashMap保存需要生成UUID的接口，只用一个线程轮询map生成UUID
 * @author: mpf
 **/
@Component
@Scope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public class DynamicTask {

    private final static Logger logger = LoggerFactory.getLogger(DynamicTask.class);

    @Autowired
    private RedisUtil redisUtil;
//    @Resource
//    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
//    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(10);
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }

    private ScheduledFuture<?> future;

    public boolean startCron(String id, int limit) {
        String cron = "0/1 * * * * ?";
        System.out.println(Thread.currentThread().getName());
        if(!rateLimiterMap.containsKey(id)){
            future = threadPoolTaskScheduler().schedule(new myTask(id, limit), new CronTrigger(cron));
            rateLimiterMap.put(id, future);
            return true;
        }
        return false;
    }

    public void stop() {
        if (future != null) {
            future.cancel(true);
        }
    }

    private class myTask implements Runnable {
        private String name;
        private int limit;

        myTask(String name, int limit) {
            this.name = name;
            this.limit = limit;
        }

        @Override
        public void run() {
//            System.out.println("function => " + name);
            redisUtil.pushUUID(name, limit);
        }
    }

}