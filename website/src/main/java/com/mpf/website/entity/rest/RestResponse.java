package com.mpf.website.entity.rest;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: MiaoPengfei
 * @date: 2021/2/17 20:39
 * @description:
 * @since: 1.0.20
 */
@Data
public class RestResponse {

    public static final int SUCCESS = 200;
    public static final int NOT_FOUND = 404;
    public static final int ERROR = 500;

    @ApiModelProperty(value = "状态码")
    private int status;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "数据")
    private Object data;

    public RestResponse() {
        this.status = RestResponse.NOT_FOUND;
        this.message = "fail";
    }

    public RestResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public void successBuilder(Object... data) {
        this.status = RestResponse.SUCCESS;
        this.message = "success";
        if (data.length > 0) {
            this.data = data[0];
        }
    }
}
