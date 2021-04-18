package com.mpf.starter.kafka.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicMessage {
	/**
	 * 发送数据key
	 */
    private String key;

	/**
	 * 发送数据信息data
	 */
	private Map<String, Object> data;
}
