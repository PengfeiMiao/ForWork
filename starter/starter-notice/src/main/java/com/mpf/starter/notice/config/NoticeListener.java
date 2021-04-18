package com.mpf.starter.notice.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mpf.starter.notice.constant.NoticeChannel;
import com.mpf.starter.notice.entity.message.NoticeMessage;
import com.mpf.starter.notice.entity.message.NoticeMessageType;
import com.mpf.starter.notice.server.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 消息通知监听器
 *
 * @author egoo
 * @date 2020/3/17
 */
@Slf4j
public class NoticeListener implements MessageListener {

	@Override
	public void onMessage(Message message, byte[] bytes) {
		String channel = new String(message.getChannel(), StandardCharsets.UTF_8);
		String content = new String(message.getBody(), StandardCharsets.UTF_8);
		if (log.isInfoEnabled()) {
			log.info("web socket receive msg {}, {}", channel, content);
		}
		if(channel.equals(NoticeChannel.PUBLISH_CHANNEL)) {
			Map map = JSON.parseObject(content, Map.class);
			String type = (String) map.get("type");
			JSONObject obj = (JSONObject)map.get("msg");
			NoticeMessage msg = obj.toJavaObject(NoticeMessage.class);
			String userId = (String) map.get("userId");

			if (log.isInfoEnabled()) {
				log.info("web socket send msg {}, {}, {}", type, userId, msg);
			}
			if(NoticeMessageType.BROADCAST.equals(type)) {
				WebSocketServer.broadcast(JSON.toJSONString(msg));
			} else if(NoticeMessageType.PEER_MSG.equals(type)) {
				WebSocketServer.sendMessage(JSON.toJSONString(msg), userId);
			}
		}
	}
}
