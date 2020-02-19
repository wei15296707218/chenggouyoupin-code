package com.ssw.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @BelongsProject: cgyp_product_category
 * @BelongsPackage: com.ssw.config
 * @Author: Wss
 * @CreateTime: 2020-02-15 14:26
 * @Description: redisson配置
 */
@Configuration
public class RedissonConfig {

    @Value("${redis.ip}")
    private String ip;

    @Value("${redis.port}")
    private Integer port;

    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+ip+":"+port);
        RedissonClient redissonClient = Redisson.create(config);

        return redissonClient;
    }
}