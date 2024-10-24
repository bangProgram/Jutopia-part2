package com.jbproject.jutopia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class JutopiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JutopiaApplication.class, args);
	}

}
