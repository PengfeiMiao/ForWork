package com.mpf.study.config.mq;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;

/**
 * @author xiaoyin
 * @date 2018/12/13.
 */
@Data
@SpringBootConfiguration
public class MQProducerConfiguration {

    @Value("${apache.rocketmq.producer.groupName}")
    private String groupName;

    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

}
