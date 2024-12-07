package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.project")
public class GameRecommendationProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameRecommendationProjectApplication.class, args);
	}

}
