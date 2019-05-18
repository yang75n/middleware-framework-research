package com.yqw.boot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * springBoot整合Listener方式一
 *
 *
 */
@SpringBootApplication
@ServletComponentScan
public class FirstListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstListenerApplication.class, args);
	}

}
