package com.mpf.study.config.kafka;//package com.mpf.forwork.config.kafka;
//
//import com.mpf.forwork.config.KafkaConfig;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.Properties;
//
///**
// * @author mpf
// * @version 1.0
// * @date 2020/7/8 18:56
// */
//@Component
//public class KafkaConsumer implements InitializingBean {
//    public String topic = KafkaConfig.topic;
//    @Autowired
//    KafkaConfig kafkaConfig;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        //每个线程一个KafkaConsumer实例，且线程数设置成分区数，最大化提高消费能力
//        int consumerThreadNum = 2;//线程数设置成分区数，最大化提高消费能力
//        for (int i = 0; i < consumerThreadNum; i++) {
//            new KafkaConsumerThread(kafkaConfig.customerConfigs(), topic).start();
//        }
//    }
//
//    public class KafkaConsumerThread extends Thread {
//        private org.apache.kafka.clients.consumer.KafkaConsumer<String, String> kafkaConsumer;
//
//        public KafkaConsumerThread(Properties props, String topic) {
//            this.kafkaConsumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
//            this.kafkaConsumer.subscribe(Arrays.asList(topic));
//        }
//
//        @Override
//        public void run() {
//            try {
//                while (true) {
//                    ConsumerRecords<String, String> records =
//                            kafkaConsumer.poll(Duration.ofMillis(100));
//                    for (ConsumerRecord<String, String> record : records) {
//                        System.out.println("message------------ "+record.value());
//                    }
//                }
//            } catch (Exception e) {
//            } finally {
//                kafkaConsumer.close();
//            }
//        }
//    }
//}
//
//
