package com.klm.exercise.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.klm.exercise.service.StockPriceService;

@RestController
public class StockPriceController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceController.class);

	@Autowired
	private StockPriceService stockPriceService;

	/**
	 * This function handles request to get the list of close rate of stock over time.
	 * Time can be year, month within a year or day within a month of year. At
	 * least year should be present in the request to get the close price.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/getCloseRateOverTime/time")
	public Map<String, BigDecimal> getCloseRateOverTime(@RequestParam(required = false) String year,
			@RequestParam(required = false) String month, @RequestParam(required = false) String day)
			throws IOException {
		LOGGER.info("Entering controller method to get the close price of stock over time");
		return stockPriceService.getCloseRateOverTime(year, month, day);
	}

	/**
	 * This function handles request to get the average close rate of stock over
	 * a period. Period can be year, month within a year or day within a month
	 * of year. At least year should be present in the request to get the close
	 * price.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/getAverageCloseRateOverPeriod/period")
	public Map<String, BigDecimal> getAverageCloseRateOverPeriod(@RequestParam(required = false) String year,
			@RequestParam(required = false) String month, @RequestParam(required = false) String day)
			throws IOException {
		LOGGER.info("Entering controller method to get the average close price of stock over a period");
		return stockPriceService.getAverageCloseRateOverPeriod(year, month, day);
	}

	/**
	 * This function handles all other requests.
	 * 
	 * @param reqParam
	 * @return
	 */
	@RequestMapping(value = { "/**", "/getCloseRateOverTime", "/getAverageCloseRateOverPeriod" }, method = {
			RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<String> defaultPath() {
		LOGGER.info("Entering controller method to handle unrecognised resource");
		return new ResponseEntity<String>("Unmapped request. Please provide the correct request.", HttpStatus.BAD_REQUEST);
	}
}
