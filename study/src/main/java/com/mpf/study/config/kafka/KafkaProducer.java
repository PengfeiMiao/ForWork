package com.mpf.study.config.kafka;//package com.mpf.forwork.config.kafka;
//
//import com.mpf.forwork.config.KafkaConfig;
//import org.apache.kafka.clients.producer.Callback;
//import org.apache.kafka.clients.producer.Producer;
//import org.apache.kafka.clients.producer.ProducerRecord;
//import org.apache.kafka.clients.producer.RecordMetadata;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
///**
// * @author mpf
// * @version 1.0
// * @date 2020/7/8 18:55
// */
//@Component
//public class KafkaProducer{
//
//    public String topic = KafkaConfig.topic;
//
//    @Autowired
//    Producer producer;
//
//    public void producer() throws Exception {
//
//        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "hello, Kafka!");
//        try {
//            producer.send(record, new Callback() {
//                @Override
//                public void onCompletion(RecordMetadata metadata, Exception exception) {
//                    if (exception == null) {
//                        System.out.println(metadata.partition() + ":" + metadata.offset());
//                    }
//                }
//            });
//        } catch (Exception e) {
//        }
//    }
//}
