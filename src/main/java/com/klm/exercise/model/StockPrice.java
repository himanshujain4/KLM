package com.klm.exercise.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class StockPrice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private LocalDate date;
	
	@Column(precision=10, scale=2)
	private BigDecimal openPrice;
	
	@Column(precision=10, scale=2)
	private BigDecimal highPrice;
	
	@Column(precision=10, scale=2)
	private BigDecimal lowPrice;
	
	@Column(precision=10, scale=8)
	private BigDecimal closePrice;
	
	@Column(precision=10, scale=8)
	private BigDecimal adjClosePrice;
	
	private Integer volume;

}
