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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.klm.exercise.service.StockPriceService;

@RestController
public class StockPriceController {
	private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceController.class);

	@Autowired
	private StockPriceService stockPriceService;

	/**
	 * This function handles request to get the close price of stock for entire csv file data.
	 * 
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/getClosePriceForEntireTimeSpanOfFile")
	public Map<String, BigDecimal> getClosePriceForEntireTimeSpanOfFile() throws IOException {
		LOGGER.info("Entering controller method to get the close price of stock for entire csv file data");
		return stockPriceService.getClosePriceForEntireTimeSpanOfFile();
	}

	/**
	 * 
	 * This function handles request to get the close price of stock within two dates. 
	 * Date format should be dd-MM-yyyy.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/getClosePriceBetweenDates/{fromDate}/{toDate}")
	public Map<String, BigDecimal> getClosePriceBetweenDates(@PathVariable String fromDate, @PathVariable String toDate) throws IOException {
		LOGGER.info("Entering controller method to get the close price of stock from " + fromDate + " to" + toDate);
		return stockPriceService.getClosePriceBetweenDates(fromDate, toDate);
	}
	
	
	/**
	 * This function handles request to get the average close price of stock within a year.
	 * 
	 * @param year
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/getAverageClosePriceWithinYear/{year}")
	public Map<String, BigDecimal> getAverageClosePriceWithinYear(@PathVariable String year) throws IOException {
		LOGGER.info("Entering controller method to get the average close price of stock for year " + year);
		return stockPriceService.getAverageClosePriceWithinYear(year);
	}

	/**
	 * This function handles request to get the average close price of stock within a month of
	 * a year.
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/getAverageClosePriceWithinMonthOfYear/{year}/{month}")
	public Map<String, BigDecimal> getAverageClosePriceWithinMonthOfYear(@PathVariable String year, @PathVariable String month) throws IOException {
		LOGGER.info("Entering controller method to get the average close price of stock for month  " + month + "of the year" + year);
		return stockPriceService.getAverageClosePriceWithinMonthOfYear(year, month);
	}

	/**
	 * This function handles request to get the close price of stock for a particular date.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/getClosePriceForDate/{year}/{month}/{day}")
	public Map<String, BigDecimal> getClosePriceForDate(@PathVariable String year, @PathVariable String month, @PathVariable String day) throws IOException {
		LOGGER.info("Entering controller method to get the close price of stock for date " + day + "-" + month + "-" + year);
		return stockPriceService.getClosePriceForDate(year, month, day);
	}

	/**
	 * This function handles all other requests.
	 * 
	 * @param reqParam
	 * @return
	 */
	@RequestMapping(value = "/**", method = { RequestMethod.GET, RequestMethod.POST })
	public ResponseEntity<String> defaultPath() {
		LOGGER.info("Entering controller method to handle unrecognised resource");
		return new ResponseEntity<String>("Unmapped request. Please provide the correct request.", HttpStatus.BAD_REQUEST);
	}
}
