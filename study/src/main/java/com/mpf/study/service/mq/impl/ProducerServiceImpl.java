package com.mpf.study.service.mq.impl;

import com.mpf.study.config.mq.MQProducerConfiguration;
import com.mpf.study.service.mq.ProducerService;
import com.mpf.study.staticobject.CommonStatic;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author xiaoyin
 * @date 2018/12/13
 */
@Service
public class ProducerServiceImpl implements ProducerService {
    @Autowired
    private MQProducerConfiguration mqProducerConfiguration;

    private DefaultMQProducer producer;

    /**
     * 初始化生产者
     */
    @PostConstruct
    public void initialMQProducer() {
        if (mqProducerConfiguration == null) {
            return;
        }
        this.producer = new DefaultMQProducer(mqProducerConfiguration.getGroupName());
        this.producer.setNamesrvAddr(mqProducerConfiguration.getNamesrvAddr());

        try {
            this.producer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.producer.setRetryTimesWhenSendFailed(3);
        this.producer.setRetryTimesWhenSendAsyncFailed(3);
    }


    @Override
    public void send(String topic, String msg) {
        Message message = new Message(topic, msg.getBytes());
        try {
            SendResult sendResult = producer.send(message);
            System.out.println("sendResult:" + sendResult.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(Message message) {
        try {
            SendResult sendResult = producer.send(message);
            System.out.println("sendResult:" + sendResult.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMsgAsynchronously(String topic, String msg) throws
            Exception {

        Message message = new Message(CommonStatic.TOPIC, msg.getBytes());
        producer.send(message, new SendCallback() {

            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult.toString());
                System.err.printf("%-10d OK %s %n", sendResult.getMsgId());
            }

            @Override
            public void onException(Throwable e) {
                System.out.println("3:" + e.getMessage());
                System.err.printf("%-10d Exception %s %n", e);
                e.printStackTrace();
            }
        });
    }


    /**
     * 关闭生产者
     */
    @PreDestroy
    public void shutDownProducer() {
        if (this.producer != null) {
            this.producer.shutdown();
        }
    }

}
