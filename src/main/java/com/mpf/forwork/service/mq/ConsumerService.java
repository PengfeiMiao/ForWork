package com.mpf.forwork.service.mq;


public interface ConsumerService {

    void subscribe(String topic, String subExpression);

      /**
       * 延时一秒发送邮件
       * @param topic
       * @author xiaoyin
       * @date 2018/12/13 16:29
       * @return
       */
    void subscribeSendEmail(String topic);

}
