package com.swang.jpaweb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.swang.jpaweb.config.GithubProperties;

@SpringBootTest
class JpawebApplicationTests {
	@Autowired
	private GithubProperties properties;

	@Test
	void contextLoads() {
	}

	@Test
	void getToken() {
		assertThat(properties.getToken()).startsWith("wangshane3");
	}
}