package com.mpf.website.controller;

import com.mpf.website.entity.rest.RestResponse;
import com.mpf.website.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: MiaoPengfei
 * @date: 2021/2/15 10:44
 * @description:
 * @since: 1.0.20
 */
@Slf4j
@RestController
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

}
