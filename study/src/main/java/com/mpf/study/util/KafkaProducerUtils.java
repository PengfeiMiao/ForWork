package com.mpf.study.util;

/**
 * @author mpf
 * @version 1.0
 * @date 2020/7/23 9:54
 */

import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;
import java.util.Properties;

public class KafkaProducerUtils {

    @Value("${spring.kafka.bootstrap-servers}")
    private String servers;

    /**
     * 私有静态方法，创建Kafka生产者
     *
     * @return KafkaProducer
     * @author IG
     * @Date 2017年4月14日 上午10:32:32
     * @version 1.0.0
     */
    private KafkaProducer<String, String> createProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", servers);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 0);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<String, String>((props));
    }

    /**
     * 传入kafka约定的topicName,json格式字符串，发送给kafka集群
     *
     * @param topicName
     * @param jsonMessage
     * @author IG
     * @Date 2017年4月14日 下午1:29:09
     * @version 1.0.0
     */
    public void sendMessage(String topicName, String jsonMessage) {
        KafkaProducer<String, String> producer = createProducer();
        producer.send(new ProducerRecord<>(topicName, jsonMessage));
        producer.close();
    }

    /**
     * 传入kafka约定的topicName,json格式字符串数组，发送给kafka集群<br>
     * 用于批量发送消息，性能较高。
     *
     * @param topicName
     * @param jsonMessages
     * @throws InterruptedException
     * @author IG
     * @Date 2017年4月14日 下午2:00:12
     * @version 1.0.0
     */
    public void sendMessage(String topicName, String... jsonMessages) {
        KafkaProducer<String, String> producer = createProducer();
        for (String jsonMessage : jsonMessages) {
            producer.send(new ProducerRecord<>(topicName, jsonMessage));
        }
        producer.close();
    }

    /**
     * 传入kafka约定的topicName,Map集合，内部转为json发送给kafka集群 <br>
     * 用于批量发送消息，性能较高。
     *
     * @param topicName
     * @param mapMessageToJSONForArray
     * @author IG
     * @Date 2017年4月14日 下午2:01:18
     * @version 1.0.0
     */
    public void sendMessage(String topicName, List<Map<Object, Object>> mapMessageToJSONForArray) {
        KafkaProducer<String, String> producer = createProducer();
        for (Map<Object, Object> mapMessageToJSON : mapMessageToJSONForArray) {
            String array = JSONObject.toJSONString(mapMessageToJSON);
            producer.send(new ProducerRecord<>(topicName, array));
        }
        producer.close();
    }

    /**
     * 传入kafka约定的topicName,Map，内部转为json发送给kafka集群
     *
     * @param topicName
     * @param mapMessageToJSON
     * @author IG
     * @Date 2017年4月14日 下午1:30:10
     * @version 1.0.0
     */
    public void sendMessage(String topicName, Map<Object, Object> mapMessageToJSON) {
        KafkaProducer<String, String> producer = createProducer();
        String array = JSONObject.toJSONString(mapMessageToJSON);
        producer.send(new ProducerRecord<>(topicName, array));
        producer.close();
    }
}
