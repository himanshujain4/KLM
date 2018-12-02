package com.klm.exercise;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.klm.exercise.controller.StockPriceController;

@SpringBootApplication
public class KlmExerciseApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(KlmExerciseApplication.class);
	
	public static void main(String[] args) {
		LOGGER.info("Entring into application KlmExerciseApplication");
		SpringApplication.run(KlmExerciseApplication.class, args);
	}
	
	@Bean
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
	    return hemf.getSessionFactory();
	}
}
