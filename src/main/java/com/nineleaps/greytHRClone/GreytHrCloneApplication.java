package com.nineleaps.greytHRClone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@SpringBootApplication
public class GreytHrCloneApplication {

	public static void main(String[] args) {
		System.out.println("hello");
		SpringApplication.run(GreytHrCloneApplication.class, args);
	}

}
