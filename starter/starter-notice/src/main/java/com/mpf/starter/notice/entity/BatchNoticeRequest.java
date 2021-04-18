package com.mpf.starter.notice.entity;

import com.mpf.starter.notice.entity.message.NoticeMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;

/**
 * 批量发送通知请求
 *
 * @author egoo
 * @date 2019/4/8
 */
@Data
@ApiModel("WebSocket批量发送消息推送接口")
public class BatchNoticeRequest {
	@ApiModelProperty("用户ID")
	private List<String> userIds;

	@ApiModelProperty("请求消息内容")
	private NoticeMessage message;
}
