package com.swang.jpaweb;

import com.swang.jpaweb.config.GithubProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableConfigurationProperties(GithubProperties.class)
@EnableCaching
@SpringBootApplication
public class JpawebApplication {
	public static void main(String[] args) {
		SpringApplication.run(JpawebApplication.class, args);
	}
}