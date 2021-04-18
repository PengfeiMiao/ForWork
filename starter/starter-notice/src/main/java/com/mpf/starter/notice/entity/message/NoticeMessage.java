package com.mpf.starter.notice.entity.message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * REST API请求事件
 *
 * @author egoo
 * @date 2019/4/8
 */
@Data
@ApiModel("WebSocket消息推送接口")
public class NoticeMessage {
	@ApiModelProperty("WebSocket消息类型")
	private String type;

	@ApiModelProperty("WebSocket消息ID")
	private String msgId;

	@ApiModelProperty("WebSocket消息体 Object类型")
	private Object body;
}
