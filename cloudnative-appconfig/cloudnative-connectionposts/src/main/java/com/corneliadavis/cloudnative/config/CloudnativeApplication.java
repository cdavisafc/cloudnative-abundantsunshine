package com.corneliadavis.cloudnative.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = { "com.corneliadavis.cloudnative.*" })
@EntityScan("com.corneliadavis.cloudnative.*")
public class CloudnativeApplication {

	@Value("${redis.hostname}")
	private String redisHostName;
	@Value("${redis.port}")
	private int redisPort;

	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(redisHostName);
		factory.setPort(redisPort);
		factory.setUsePool(false);
		return factory;
	}

	public static void main(String[] args) { SpringApplication.run(CloudnativeApplication.class, args); }
}
