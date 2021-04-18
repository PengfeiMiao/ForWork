package com.mpf.starter.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * kafka通用生产者配置
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "kafka.enabled.producer", havingValue = "true")
public class BasicProducer {
	@Autowired
	@Qualifier("basicKafkaTemplate")
	private KafkaTemplate<String, BasicMessage> kafkaTemplate;

	public void send(String topic, BasicMessage basic) throws Exception {
		kafkaTemplate.send(topic, basic).get();
	}
}
