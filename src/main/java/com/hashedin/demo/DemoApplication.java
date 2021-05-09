package com.hashedin.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableAutoConfiguration
@SpringBootApplication ()
@ComponentScan(basePackages = {"com.hashedin.controller", "com.hashedin.service", "com.hashedin.repository","com.hashedin.utils"})
@EntityScan("com.hashedin.model")
@EnableJpaRepositories("com.hashedin.repository")
@EnableSwagger2
@EnableScheduling
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
