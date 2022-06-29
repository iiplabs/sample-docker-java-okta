package com.iiplabs.dale.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.iiplabs.dale.web.utils.AuditorAwareImpl;

@EnableJpaAuditing(modifyOnCreate = false, auditorAwareRef = "auditorAware")
@PropertySource("classpath:config.properties")
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

}
