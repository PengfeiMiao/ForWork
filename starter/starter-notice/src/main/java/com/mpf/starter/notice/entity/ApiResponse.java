package com.mpf.starter.notice.entity;

import com.mpf.starter.notice.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 通用接口返回值
 *
 * @author egoo
 * @date 2019/3/28
 */
@Data
@ApiModel("HTTP请求接口返回值")
public class ApiResponse<T> implements Serializable {
	@ApiModelProperty("返回值 Http Status 200，404，500")
	private Integer code;

	@ApiModelProperty("成功失败状态值success/error/fail")
	private String status;

	@ApiModelProperty("返回消息")
	private String message;

	@ApiModelProperty("数据追踪ID")
	private String traceID;

	@ApiModelProperty("返回结果集合")
	private T data;

	@ApiModelProperty("其他备注信息")
	private Object attach;

	public static ApiResponse successResponse() {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.code = HttpStatus.OK.value();
		apiResponse.status = "success";
		apiResponse.message = "success";
		return apiResponse;
	}

	public static ApiResponse responseBuilder(int retCode) {
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.set(retCode);
		return apiResponse;
	}

	private void set(int code) {
		this.setCode(code);
		if(code >= 100 && code <= 399 ) {
			this.setStatus(ApiResponseStatus.SUCCESS);
			this.setMessage(CommonConstant.DEFAULT_SUCCESS_MESSAGE);
		}
		if(code >= 400 && code <= 499 ) {
			this.setStatus(ApiResponseStatus.ERROR);
			this.setMessage(CommonConstant.DEFAULT_FAILURE_MESSAGE);
		}
		if(code >= 500 && code <= 599 ) {
			this.setStatus(ApiResponseStatus.FAIL);
			this.setMessage(CommonConstant.DEFAULT_FAILURE_MESSAGE);
		}
	}

	/**
	 * 返回R
	 *
	 * @param data 数据
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> ApiResponse<T> data(T data) {
		return data(data, "success");
	}

	/**
	 * 返回R
	 *
	 * @param data 数据
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> ApiResponse<T> data(T data, String msg) {
		return data(HttpServletResponse.SC_OK, data, msg);
	}

	/**
	 * 返回R
	 *
	 * @param code 状态码
	 * @param data 数据
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> ApiResponse<T> data(int code, T data, String msg) {
		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.setCode(code);
		apiResponse.setMessage(msg);
		apiResponse.setData(data);
		return apiResponse;
	}

	/**
	 * 返回R
	 *
	 * @param code 状态码
	 * @param msg  消息
	 * @param <T>  T 泛型标记
	 * @return R
	 */
	public static <T> ApiResponse<T> code(int code, String msg) {
		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.setCode(code);
		apiResponse.setMessage(msg);
		return apiResponse;
	}

	/**
	 * 返回R
	 *
	 * @param flag 成功状态
	 * @return R
	 */
	public static <T> ApiResponse<T> status(boolean flag) {
		return flag ? code(HttpStatus.OK.value(),"success") : code(HttpStatus.BAD_REQUEST.value(), "fail");
	}

 	/**
	 *
	 * @param code 状态码
	 * @param status 状态
	 * @param message 返回信息
	 * @param <T>
	 * @return
	 */
	public static <T> ApiResponse <T> message(Integer code, String status, String message) {
		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.setCode(code);
		apiResponse.setStatus(status);
		apiResponse.setMessage(message);
		return apiResponse;
	}

	/**
	 * 响应200
	 * @param <T>
	 * @return
	 */
	public static <T> ApiResponse <T> successResponse(T data) {
		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.setCode(200);
		apiResponse.setStatus("success");
		apiResponse.setData(data);
		return apiResponse;
	}
	public static <T> ApiResponse <T> errorResponse(T data) {
		ApiResponse<T> apiResponse = new ApiResponse<>();
		apiResponse.setCode(500);
		apiResponse.setStatus("error");
		apiResponse.setData(data);
		return apiResponse;
	}


}
