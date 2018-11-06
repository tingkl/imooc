package com.mix.miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiaoshaApplication {
	public static void main(String[] args) {
		SpringApplication.run(MiaoshaApplication.class, args);
	}
}
//public class MiaoshaApplication extends SpringBootServletInitializer {
//	public static void main(String[] args) {
//		SpringApplication.run(MiaoshaApplication.class, args);
//	}
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(MiaoshaApplication.class);
//	}
//}
