package com.mpf.forwork.service.mq.impl;

import com.google.gson.Gson;
import com.mpf.forwork.config.mq.MQConsumerConfiguration;
import com.mpf.forwork.messageobj.SingleMailSendObj;
import com.mpf.forwork.service.MailSendService;
import com.mpf.forwork.service.mq.ConsumerService;
import com.mpf.forwork.staticobject.CommonStatic;
import com.mpf.forwork.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
public class ConsumerServiceImpl implements ConsumerService {


    @Autowired
    private MQConsumerConfiguration mqConsumerConfiguration;
    private DefaultMQPushConsumer consumer;

    @Autowired
    private MailSendService mailSendService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 初始化消费者
     */
    @PostConstruct
    public void initialMQConsumer() throws MQClientException {
        if (mqConsumerConfiguration == null) {
            return;
        }
        this.consumer = new DefaultMQPushConsumer(mqConsumerConfiguration.getGroupName());
        this.consumer.setNamesrvAddr(mqConsumerConfiguration.getNamesrvAddr());
//      消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
        this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);

    }

    @Override
    public void subscribe(String topic, String subExpression) {
        try {
            consumer.subscribe(topic, "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                                                            ConsumeConcurrentlyContext context) {
                System.out.printf("Consumer=>%s Received", Thread.currentThread().getName());

                for(Message msg:msgs) {
                    //消费者获取消息 这里只输出 不做后面逻辑处理
                    String body = new String(msg.getBody(), StandardCharsets.UTF_8);
                    log.info("\n主题topic=>{}, 消费消息=>{}",msg.getTopic(),body);
//                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    redisUtil.set("mq", body);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        try {
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribeSendEmail(String topic) {
        try {
            consumer.subscribe(topic, "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt messageExt : msgs) {
                    try {
                        System.out.println("邮件每隔一秒发送");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String objJson = new String(messageExt.getBody());
                    Gson gson = new Gson();
                    SingleMailSendObj mailSendObj = gson.fromJson(objJson, SingleMailSendObj.class);
                    System.out.println("开始发送目标账号 " + mailSendObj.getRecipientMail());
                    String recipientMail=mailSendObj.getRecipientMail();
                    String mailTheme=mailSendObj.getMailTheme();
                    String mailContent=mailSendObj.getMailContent();
                    List<String> attachmentPathList=mailSendObj.getAttachmentPathList();
                    mailSendService.inParamCallSendMail(recipientMail,mailTheme,mailContent,attachmentPathList);

                }
                return ConsumeOrderlyStatus.SUCCESS;

            }
        });
        try {
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

}
