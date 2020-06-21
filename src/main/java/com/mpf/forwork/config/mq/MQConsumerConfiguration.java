package com.mpf.forwork.config.mq;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;

/**
 * @author xiaoyin
 * @date 2018/12/13.
 */
@Data
@SpringBootConfiguration
public class MQConsumerConfiguration {

    @Value("${apache.rocketmq.consumer.groupName}")
    private String groupName;

    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;
}
