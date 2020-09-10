package com.nineleaps.greytHRClone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
public class GreytHrCloneApplication {

	public static void main(String[] args) {
		System.out.println("hello");
		SpringApplication.run(GreytHrCloneApplication.class, args);
	}

}
