package com.mpf.study.service.mq.impl;

import com.google.gson.Gson;
import com.mpf.study.config.mq.MQConsumerConfiguration;
import com.mpf.study.messageobj.SingleMailSendObj;
import com.mpf.study.service.MailSendService;
import com.mpf.study.service.mq.MailConsumerService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class MailConsumerServiceImpl implements MailConsumerService {
    @Autowired
    private MQConsumerConfiguration mqConsumerConfiguration;
    private DefaultMQPushConsumer consumer;

    @Autowired
    private MailSendService mailSendService;

    /**
     * 初始化消费者
     */
    /**
     * 初始化消费者
     */
    @PostConstruct
    public void initialMQConsumer() {
        if (mqConsumerConfiguration == null) {
            return;
        }
        this.consumer = new DefaultMQPushConsumer(mqConsumerConfiguration.getGroupName());
        this.consumer.setNamesrvAddr(mqConsumerConfiguration.getNamesrvAddr());
        this.consumer.setConsumeThreadMin(1);
        this.consumer.setConsumeThreadMax(1);
        this.consumer.setPullInterval(5000);
        this.consumer.setPullBatchSize(1);
    }

    @Override
    public void subscribeSendMail(String topic) {
        try {
            consumer.subscribe(topic, "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        //监听
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt messageExt : msgs) {
                    String objJson = new String(messageExt.getBody());
                    Gson gson = new Gson();
                    SingleMailSendObj mailSendObj = gson.fromJson(objJson, SingleMailSendObj.class);
                    System.out.println("开始发送目标账号 " + mailSendObj.getRecipientMail());
                    String recipientMail = mailSendObj.getRecipientMail();
                    String mailTheme = mailSendObj.getMailTheme();
                    String mailContent = mailSendObj.getMailContent();
                    List<String> attachmentPathList = mailSendObj.getAttachmentPathList();
                    mailSendService.inParamCallSendMail(recipientMail, mailTheme, mailContent, attachmentPathList);
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
