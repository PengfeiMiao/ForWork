package com.mpf.starter.notice.config;

import com.mpf.starter.notice.constant.NoticeChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * 消息订阅
 *
 * @author egoo
 * @date 2020/3/17
 */
@Configuration
public class NoticeListenerConfig {
	@Autowired
	RedisConnectionFactory redisConnectionFactory;

	@Bean
	MessageListenerAdapter listenerAdapter() {
		return new MessageListenerAdapter(new NoticeListener());
	}

	@Bean
	public RedisMessageListenerContainer container() {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory);
		container.addMessageListener(listenerAdapter(), new PatternTopic(NoticeChannel.PUBLISH_CHANNEL));
		return container;
	}
}
