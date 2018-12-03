package com.klm.exercise;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StockPriceApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceApplication.class);
	
	public static void main(String[] args) {
		LOGGER.info("Entring into application StockPriceApplication");
		SpringApplication.run(StockPriceApplication.class, args);
	}
	
	@Bean
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
	    return hemf.getSessionFactory();
	}
}
