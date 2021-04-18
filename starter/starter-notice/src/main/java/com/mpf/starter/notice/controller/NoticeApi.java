package com.mpf.starter.notice.controller;

import com.alibaba.fastjson.JSON;
import com.mpf.starter.notice.entity.BatchNoticeRequest;
import com.mpf.starter.notice.entity.message.NoticeMessage;
import com.mpf.starter.notice.entity.message.NoticeMessageType;
import com.mpf.starter.notice.server.WebSocketServer;
import com.mpf.starter.notice.entity.ApiResponse;
import com.mpf.starter.notice.constant.NoticeChannel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 消息通知对外接口
 *
 * @author egoo
 * @date 2019/3/28
 */
@RestController
@RequestMapping(value = "/v1/notice/message", produces = "application/json")
@Api(value="消息通知接口", tags="NoticeApi", description="消息通知接口管理")
@Slf4j
public class NoticeApi {

	@Autowired
	private RedisTemplate redisTemplate;

	@ApiOperation(value="发送消息给指定用户", notes="发送消息给指定用户")
	@RequestMapping(value = "/peer/{userId}", method = RequestMethod.POST)
	public ApiResponse sendNoticeToPeer(@PathVariable String userId, @RequestBody NoticeMessage message) {
		if(log.isInfoEnabled()) {
			log.info("receive peer message request to userId: {}, content: {}", userId, message);
		}
		publishPeerMessage(userId, message);
		ApiResponse apiResponse = ApiResponse.successResponse();
		return apiResponse;
	}

	@ApiOperation(value="发送批量消息给指定用户", notes="发送批量消息给指定用户")
	@RequestMapping(value = "/peer/batch/{userId}", method = RequestMethod.POST)
	public ApiResponse sendBatchNoticeToPeer(@PathVariable String userId, @RequestBody List<NoticeMessage> messages) {
		ApiResponse apiResponse = ApiResponse.successResponse();
		if(log.isInfoEnabled()) {
			log.info("receive broadcast message request with content: {}", messages.toString());
		}
		for(NoticeMessage message: messages) {
			publishPeerMessage(userId, message);
		}
		return apiResponse;
	}

	@ApiOperation(value="发送消息给指定用户组", notes="发送消息给指定用户组")
	@RequestMapping(value = "/peer/batch/user", method = RequestMethod.POST)
	public ApiResponse sendNoticeToBatchUser(@RequestBody BatchNoticeRequest batchNoticeRequest) {
		ApiResponse apiResponse = ApiResponse.successResponse();
		if(log.isInfoEnabled()) {
			log.info("receive batch message request with content: {}", batchNoticeRequest.toString());
		}

		List<String> userIds = batchNoticeRequest.getUserIds();
		NoticeMessage message = batchNoticeRequest.getMessage();
		if(ObjectUtils.isEmpty(userIds) || ObjectUtils.isEmpty(message)) {
			apiResponse.setMessage("receive batch message or user is null, send none message!!");
			return apiResponse;
		}

		for(String userId: userIds) {
			publishPeerMessage(userId, message);
		}
		apiResponse.setMessage("receive batch message size: " + userIds.size());
		return apiResponse;
	}

	@ApiOperation(value="发送广播消息给所有在线用户", notes="发送广播消息给所有在线用户")
	@RequestMapping(value = "/broadcast", method = RequestMethod.POST)
	public ApiResponse sendNoticeToAll(@RequestBody NoticeMessage message) {
		ApiResponse apiResponse = ApiResponse.successResponse();
		if(log.isInfoEnabled()) {
			log.info("receive broadcast message request with content: {}", message);
		}
		publishBroadcastMessage(message);
		return apiResponse;
	}


	@ApiOperation(value="查询当前在线用户数", notes="查询当前在线用户数")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ApiResponse listOnlineUsers() {
		ApiResponse apiResponse = ApiResponse.successResponse();
		Set<String> result = WebSocketServer.getOnlineUsers();
		if(log.isInfoEnabled()) {
			log.info("get online users {}", result.toString());
		}
		apiResponse.setData(result);
		return apiResponse;
	}

	private void publishPeerMessage(String userId, NoticeMessage message ) {
		// publish
		Map<String, Object> map = new HashMap<>(3);
		map.put("type", NoticeMessageType.PEER_MSG);
		map.put("userId", userId);
		map.put("msg", message);
		redisTemplate.getConnectionFactory().getConnection().publish(NoticeChannel.PUBLISH_CHANNEL.getBytes(), JSON.toJSONString(map).getBytes());
	}

	private void publishBroadcastMessage(NoticeMessage message ) {
		// publish
		Map<String, Object> map = new HashMap<>(1);
		map.put("type", NoticeMessageType.BROADCAST);
		map.put("msg", message);
		redisTemplate.getConnectionFactory().getConnection().publish(NoticeChannel.PUBLISH_CHANNEL.getBytes(), JSON.toJSONString(map).getBytes());
	}
}
