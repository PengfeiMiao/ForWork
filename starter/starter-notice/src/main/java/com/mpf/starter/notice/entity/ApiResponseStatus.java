package com.mpf.starter.notice.entity;

import io.swagger.annotations.ApiModel;

/**
 * HTTP请求接口返回状态值
 *
 * @author egoo
 * @date 2019/9/19
 */
@ApiModel("HTTP请求接口返回状态")
public class ApiResponseStatus {
	public static String SUCCESS = "success";
	public static String ERROR = "error";
	public static String FAIL = "fail";
}
