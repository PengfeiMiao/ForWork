package com.mpf.starter.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <p>启动应用程序主函数</p>
 * Spring Boot Package Plugin打包时每一个工程都需要指定一个main函数
 *
 * @author jie liu
 * @date 2018/12/29
 */
@SpringBootApplication
public class KafkaApplication {

	public static void main(String[] args) {
		System.out.println("Ops, Spring Boot Package Plugin Need main function");
		SpringApplication.run(KafkaApplication.class);
	}
}
