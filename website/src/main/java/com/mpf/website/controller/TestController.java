package com.mpf.website.controller;

import com.alibaba.fastjson.JSONObject;
import com.mpf.website.entity.rest.RestResponse;
import com.mpf.website.service.test.TestService;
import com.mpf.website.staticobj.CommonStatic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * @author: MiaoPengfei
 * @date: 2021/2/15 10:44
 * @description:
 * @since: 1.0.20
 */
@Slf4j
@RestController
@RequestMapping("/test")
@Api(value = "测试接口", tags = "测试接口")
public class TestController {

    @Autowired
    TestService testService;

    @GetMapping("/test")
    public RestResponse test() {
        RestResponse response = new RestResponse();

        try {
            String data = testService.test();
            response.setStatus(RestResponse.SUCCESS);
            response.setMessage("success");
            response.setData(data);
        } catch (Exception e) {
            log.error("fail,message:{}", e.getMessage());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @ApiOperation(value = "上传图片", notes = "editormd图片上传")
    @PostMapping("/saveImage")
    @ResponseBody
    //接收图片的参数名需要为"editormd-image-file"
    public JSONObject saveImage(@ApiParam("图片文件") @RequestParam("editormd-image-file") MultipartFile file, HttpServletRequest request) {

        JSONObject jsonObject = new JSONObject();
        /** 从request中获取 */
//        file = ((MultipartHttpServletRequest) request).getFile("editormd-image-file");
        String path = testService.uploadFile(file);
        if (StringUtils.isBlank(path)) {
            jsonObject.put("success", 0);//图片上传失败的信息码
            jsonObject.put("message", "upload error!");//信息
        } else {
            String url = "http://" + request.getServerName() + ":" + request.getServerPort() + "/" + CommonStatic.APPLICATION_NAME + "/" + path;
            jsonObject.put("url", url);//图片回显地址，即文件存放地址，应为虚拟路径
            jsonObject.put("success", 1);//图片上传成功的信息码
            jsonObject.put("message", "upload success!");//信息
        }
        return jsonObject;
    }

}