package com.mpf.study.service.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpf.study.messageobj.Message;
import com.mpf.study.staticobject.CommonStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/7/10 9:55
 */
@Component
public class KafkaProducer {

    private static Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    /**
     * 发送消息方法
     */
    public void send(Message message) {
        String msg = message.getMsg();
        message.setId(System.currentTimeMillis());
        message.setSendTime(new Date());
        message.setMsg(msg + " [" + UUID.randomUUID().toString() + "]");
        //logger.info("发送消息 => message = {}", gson.toJson(message));
        kafkaTemplate.send("test", gson.toJson(message));
    }

    /**
     * 通过线程池实现定时任务
     */
    public void sendTask() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                send(new Message(CommonStatic.MESSAGE));
            }
        }, 10, 5, TimeUnit.SECONDS);

        service.shutdown();
    }
}

