package com.example.fitnessapp;

import com.example.fitnessapp.services.MailService;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class FitnessAppApplication {

	@Autowired
	private MailService mailService;

	public static void main(String[] args) {
		SpringApplication.run(FitnessAppApplication.class, args);
	}

	@Bean

	public ModelMapper modelMapper() {

		ModelMapper modelMapper=new ModelMapper();

		modelMapper.getConfiguration().setAmbiguityIgnored(true);

		return modelMapper;

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("GET","POST", "PUT", "DELETE", "OPTIONS", "HEAD");
			}
		};
	}

}
