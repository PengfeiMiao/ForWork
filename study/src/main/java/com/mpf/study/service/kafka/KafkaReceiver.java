package com.mpf.study.service.kafka;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mpf.study.messageobj.Message;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/7/10 10:00
 */
@Component
public class KafkaReceiver {

    private static Logger logger = LoggerFactory.getLogger(KafkaReceiver.class);

    @KafkaListener(topics = {"test"})
    public void listen(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            //logger.info("----------------- record =" + record);
            //logger.info("----------------- message =" + message);
            Gson gson = new GsonBuilder().create();
            Message msg = gson.fromJson(message.toString(), Message.class);
            logger.info("kafka received => {}", msg.getMsg());
        }
    }
}

