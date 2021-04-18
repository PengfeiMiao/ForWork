package com.mpf.starter.notice.server;

import com.mpf.starter.notice.constant.OnlineUsers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Server
 *
 * @author egoo
 * @date 2019/3/27
 */
@Component
@ServerEndpoint("/v1/server/notice/message/{userId}")
@Slf4j
public class WebSocketServer {

	private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();

	private String userId;

	private Session session;

	private static RedisTemplate redisTemplate;

	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate){
		WebSocketServer.redisTemplate = redisTemplate;
	}

	@OnOpen
	public void onOpen(Session session, @PathParam("userId") String userId) {
		if(log.isInfoEnabled()) {
			log.info("receive new connection session channelId {}, userId {}", session.getId(), userId);
		}
		this.session = session;
		this.userId = userId;
		webSocketSet.put(userId, this);
		redisTemplate.opsForSet().add(OnlineUsers.ONLINE_USERS_KEY,userId);
	}

	@OnClose
	public void onClose(Session session) {
		if(log.isInfoEnabled()) {
			log.info("on close for session channelId {}", session.getId());
		}
		webSocketSet.remove(this.userId);
		redisTemplate.opsForSet().remove(OnlineUsers.ONLINE_USERS_KEY,this.userId);
	}

	@OnError
	public void onError(Session session, Throwable throwable) {
		if(log.isInfoEnabled()) {
			log.info("on error for session channelId {}, error {} {}",  session.getId(), throwable.getMessage(), throwable);
		}
	}

	@OnMessage
	public void onMessage(Session session, String message) {
		if(log.isInfoEnabled()) {
			log.info("receive message session channelId {}, content {}",  session.getId(), message);
		}
	}

	private int sendMessage(String message) {
		try {
			this.session.getBasicRemote().sendText(message);
			return HttpStatus.OK.value();
		} catch (IOException e) {
			if(log.isInfoEnabled()) {
				log.info("send message error {}, {} ", e.getMessage(), e);
			}
			return HttpStatus.EXPECTATION_FAILED.value();
		}
	}

	public static int sendMessage(String message, String userId) {
		if (webSocketSet.get(userId) == null) {
			return HttpStatus.NOT_FOUND.value();
		}
		return webSocketSet.get(userId).sendMessage(message);
	}

	public static Map<String, Integer> broadcast(String message) {
		Map<String, Integer> results = new HashMap<>(32);
		for (String key : webSocketSet.keySet()) {
			int result = webSocketSet.get(key).sendMessage(message);
			results.put(key, result);
		}
		return results;
	}

	public static Set<String> getOnlineUsers() {
		Set<String> members = WebSocketServer.redisTemplate.opsForSet().members(OnlineUsers.ONLINE_USERS_KEY);
		return members;
	}
}
