package com.mpf.study.service.mq;

public interface MailConsumerService {

    void subscribeSendMail(String topic);

}
