package com.imooc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HotDeployApplication /*extends SpringBootServletInitializer*/{

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//		return builder.sources(HotDeployApplication.class);
//	}

	public static void main(String[] args) {
		SpringApplication.run(HotDeployApplication.class, args);
	}
}
