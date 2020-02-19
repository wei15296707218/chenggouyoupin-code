package com.ssw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EnableEurekaClient
@EnableRedisHttpSession
@EnableFeignClients
@MapperScan("com.ssw.dao") //给dao接口设置实现类，并把实现类放到IOC容器里
public class PygProCateApplication {

	public static void main(String[] args) {
		SpringApplication.run(PygProCateApplication.class, args);
	}

}
