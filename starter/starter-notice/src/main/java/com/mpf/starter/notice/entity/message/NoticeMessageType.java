package com.mpf.starter.notice.entity.message;

/**
 * 网络请求消息类型
 *
 * @author egoo
 * @date 2019/4/8
 */
public class NoticeMessageType {
	public static final String HEART_BEATT = "PING_PONG";
	public static final String NOMAL_MESSAGE = "NORMAL_MESSAGE";

	// 通信类型
	public static final String PEER_MSG = "peer";
	// 单个用户发送多条数据
	public static final String BATCH_NOTICE_TO_PEER = "BatchNoticeToPeer";
	public static final String BROADCAST = "broadcast";
}
