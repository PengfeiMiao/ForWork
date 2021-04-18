package com.mpf.starter.kafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用消费者配置
 */
@Configuration
@ConditionalOnProperty(name = "kafka.enabled.consumer", havingValue = "true")
@EnableKafka
public class BasicConsumerConfig {

	@Value("${kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Value("${kafka.topic.basictopic}")
	private String topic;

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, BasicMessage> basicKafkaListenerContainerFactory() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, topic);

		ConcurrentKafkaListenerContainerFactory<String, BasicMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
		ConsumerFactory<String,BasicMessage> basicConsumerFactory =
				new DefaultKafkaConsumerFactory<>(configProps,
						new StringDeserializer(),
						new JsonDeserializer<>(BasicMessage.class));
		factory.setConsumerFactory(basicConsumerFactory);

		return factory;
	}
}
