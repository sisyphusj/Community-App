package me.sisyphusj.community.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class CommunityAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunityAppApplication.class, args);
	}

}
