package com.example.app.controller;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmorkTest {
	
    @Autowired
    HelloController controller = new HelloController();

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
}
