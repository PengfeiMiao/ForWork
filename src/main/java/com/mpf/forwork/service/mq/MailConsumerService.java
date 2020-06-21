package com.mpf.forwork.service.mq;

public interface MailConsumerService {

    void subscribeSendMail(String topic);

}
