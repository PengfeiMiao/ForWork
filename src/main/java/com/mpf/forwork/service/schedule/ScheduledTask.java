package com.mpf.forwork.service.schedule;

import com.mpf.forwork.messageobj.Message;
import com.mpf.forwork.service.kafka.KafkaProducer;
import com.mpf.forwork.staticobject.CommonStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/7/12 22:26
 */
//@Lazy(false)
//@EnableScheduling
//@Component
public class ScheduledTask implements SchedulingConfigurer {

    private static Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private KafkaProducer producer;

    private static String cron = "0/5 * * * * ?";

    public static ExecutorService newFixedThreadPool(int nThreads) {
        //new LinkedBlockingQueue<Runnable>()
        return new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactory(){
                    private AtomicInteger threadNum = new AtomicInteger(1);
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "thread-"+threadNum.getAndIncrement());
                    }
                },
                new ThreadPoolExecutor.DiscardPolicy());
    }

    public ScheduledTask() {}

    public ScheduledTask(int interval) {
        newFixedThreadPool(1).execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            cron = "0/"+ interval +" * * * * ?";
            System.err.println("cron change to: " + cron);
        });
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                // 任务逻辑
                logger.debug("dynamicCronTask is running...");
                producer.send(new Message(CommonStatic.MESSAGE));
            }
        }, triggerContext-> {
            // 任务触发，可修改任务的执行周期
            CronTrigger trigger = new CronTrigger(cron);
            Date nextExec = trigger.nextExecutionTime(triggerContext);
            return nextExec;
        });
    }
}
