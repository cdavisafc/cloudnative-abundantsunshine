package com.corneliadavis.cloudnative.config;

import com.corneliadavis.cloudnative.Utils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableJpaRepositories("com.corneliadavis.cloudnative.*")
@ComponentScan(basePackages = { "com.corneliadavis.cloudnative.*" })
@EntityScan("com.corneliadavis.cloudnative.*")
public class CloudnativeApplication {

	@Bean
	public Utils utilService() {
		return new Utils();
	}

	public static void main(String[] args) { SpringApplication.run(CloudnativeApplication.class, args); }
}
