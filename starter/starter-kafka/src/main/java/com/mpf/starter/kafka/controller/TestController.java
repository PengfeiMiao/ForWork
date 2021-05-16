package com.mpf.starter.kafka.controller;

import com.mpf.starter.kafka.config.BasicMessage;
import com.mpf.starter.kafka.config.BasicProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    BasicProducer basicProducer;

    @GetMapping("/test")
    public void test() {
        try {
            BasicMessage basicMessage = new BasicMessage();
            basicMessage.setKey("test");
            Map<String, Object> map = new HashMap<>(2);
            map.put("data", "it's a test");
            basicMessage.setData(map);
            basicProducer.send("test", basicMessage);
        } catch (Exception e) {
            log.error("fail,message:{}", e.getMessage());
        }
    }
}
