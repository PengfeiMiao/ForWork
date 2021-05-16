package com.mpf.starter.kafka.config;//package com.egoo.eeip.eeif.framework.core.config.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka消费者程序 on 19/09/2017.
 */
@Slf4j
@Component
public class BasicConsumer {

    @KafkaListener(topics = "${kafka.topic.basictopic}", containerFactory = "basicKafkaListenerContainerFactory")
    public void receive(BasicMessage basic) {
        log.info("BasicMessage, receive message: {}", basic.toString());
    }
}
