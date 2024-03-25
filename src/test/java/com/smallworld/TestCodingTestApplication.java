package com.smallworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestCodingTestApplication {

	public static void main(String[] args) {
		SpringApplication.from(CodingTestApplication::main).with(TestCodingTestApplication.class).run(args);
	}

}
