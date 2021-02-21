package com.mpf.study.service.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;

/**
 * @author xiaoyin
 * @date 2018/12/13
 */
public interface ProducerService {

    void send(String topic, String msg);

    void send(Message message);

    void sendMsgAsynchronously(String topic, String msg) throws RemotingException, MQClientException, InterruptedException, UnsupportedEncodingException, Exception;

}
