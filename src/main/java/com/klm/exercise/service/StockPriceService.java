package com.klm.exercise.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.klm.exercise.dao.StockPriceDAO;
import com.klm.exercise.model.StockPrice;
import com.klm.exercise.util.CSVReader;
import com.klm.exercise.util.Constants;
import com.klm.exercise.util.DateHelper;

@Service
public class StockPriceService {
	private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceService.class);

	@Autowired
	private CSVReader cSVReader;

	@Autowired
	private StockPriceDAO stockPriceDAO;

	@Autowired
	private DateHelper dateHelper;

	/**
	 * This function checks time parameters present in the request and return a
	 * map having time as key and close price as value.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws IOException
	 */
	public Map<String, BigDecimal> getCloseRateOverTime(String year, String month, String day) throws IOException {
		LOGGER.info("Entering service method to get the close price of stock over time");
		Map<String, BigDecimal> map = new HashMap<>();
		if (!StringUtils.isEmpty(year)) {
			if (!StringUtils.isEmpty(month)) {
				if (!StringUtils.isEmpty(day)) {
					map = getClosePriceForDate(year, month, day);
				} else {
					map = getClosePriceForMonthOfYear(year, month);
				}
			} else {
				map = getClosePriceForYear(year);
			}
		} else {
			throw new IllegalArgumentException(Constants.ILLEGAL_ARGU_EXCEPTION_MESSAGE);
		}
		return map;
	}

	/**
	 * This function checks period parameters present in the request and return
	 * a map having period as key and average close price as value.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws IOException
	 */
	public Map<String, BigDecimal> getAverageCloseRateOverPeriod(String year, String month, String day)
			throws IOException {
		LOGGER.info("Entering service method to get the average close price of stock over period");
		Map<String, BigDecimal> map = new HashMap<>();
		if (!StringUtils.isEmpty(year)) {
			if (!StringUtils.isEmpty(month)) {
				if (!StringUtils.isEmpty(day)) {
					map = getClosePriceForDate(year, month, day);
				} else {
					map = getAverageClosePriceWithinMonthOfYear(year, month);
				}
			} else {
				map = getAverageClosePriceWithinYear(year);
			}
		} else {
			throw new IllegalArgumentException(Constants.ILLEGAL_ARGU_EXCEPTION_MESSAGE);
		}
		return map;
	}

	/**
	 * This function return the close price of stock for a particular date.
	 * Returns a map having year-month-day as key and close price as value.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws IOException
	 */
	private Map<String, BigDecimal> getClosePriceForDate(String year, String month, String day) throws IOException {
		LOGGER.info(
				"Entering service method to get the close price of stock for date " + day + "-" + month + "-" + year);
		Map<String, BigDecimal> dateClosePriceMap = new HashMap<>();
		readAndSaveCSVFile();
		LocalDate date = dateHelper.getDate(year, month, day);
		BigDecimal requiredStockPrice = stockPriceDAO.getClosePriceForDate(date);
		dateClosePriceMap.put(year + Constants.DASH + month + Constants.DASH + day, requiredStockPrice);
		return dateClosePriceMap;
	}

	/**
	 * This function returns the close price of stock for a month of a year.
	 * Returns a map having year-month as key and close price as value.
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws IOException
	 */
	private Map<String, BigDecimal> getClosePriceForMonthOfYear(String year, String month) throws IOException {
		LOGGER.info(
				"Entering service method to get the close price of stock for month  " + month + "of the year" + year);
		Map<String, BigDecimal> yearMonthClosePriceMap = new HashMap<>();
		readAndSaveCSVFile();
		YearMonth yearMonth = dateHelper.getYearMonth(year, month);
		List<StockPrice> stockPriceList = stockPriceDAO.getStockPriceBetweenDates(yearMonth.atDay(1),
				yearMonth.atEndOfMonth());
		for (StockPrice stockPrice : stockPriceList) {
			yearMonthClosePriceMap.put(stockPrice.getDate().toString(), stockPrice.getClosePrice());
		}
		return yearMonthClosePriceMap;
	}

	/**
	 * This function returns the close price of stock for a year. Returns a map
	 * having year as key and close price as value.
	 * 
	 * @param year
	 * @return
	 * @throws IOException
	 */
	private Map<String, BigDecimal> getClosePriceForYear(String yr) throws IOException {
		LOGGER.info("Entering service method to get the close price of stock for year " + yr);
		Map<String, BigDecimal> yearClosePriceMap = new HashMap<>();
		readAndSaveCSVFile();
		Year year = dateHelper.getYear(yr);
		List<StockPrice> stockPriceList = stockPriceDAO.getStockPriceBetweenDates(year.atDay(1),
				year.atMonth(12).atEndOfMonth());
		for (StockPrice stockPrice : stockPriceList) {
			yearClosePriceMap.put(stockPrice.getDate().toString(), stockPrice.getClosePrice());
		}
		return yearClosePriceMap;

	}

	/**
	 * This function returns the average close price of stock within a month of
	 * a year. Returns a map having year-month as key and avg close price as
	 * value.
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws IOException
	 */
	private Map<String, BigDecimal> getAverageClosePriceWithinMonthOfYear(String year, String month)
			throws IOException {
		LOGGER.info("Entering service method to get the average close price of stock for month  " + month
				+ "of the year" + year);
		Map<String, BigDecimal> yearMonthAvgClosePriceMap = new HashMap<>();
		readAndSaveCSVFile();
		YearMonth yearMonth = dateHelper.getYearMonth(year, month);
		List<BigDecimal> closePriceList = stockPriceDAO.getClosePriceBetweenDates(yearMonth.atDay(1),
				yearMonth.atEndOfMonth());
		BigDecimal mean = getAvegareClosePrice(closePriceList);
		yearMonthAvgClosePriceMap.put(year + Constants.DASH + month, mean);
		return yearMonthAvgClosePriceMap;
	}

	/**
	 * This function returns the average close price of stock within a year.
	 * Returns a map having year as key and avg close price as value.
	 * 
	 * @param year
	 * @return
	 * @throws IOException
	 */
	private Map<String, BigDecimal> getAverageClosePriceWithinYear(String yr) throws IOException {
		LOGGER.info("Entering service method to get the average close price of stock for year " + yr);
		Map<String, BigDecimal> yearAvgClosePriceMap = new HashMap<>();
		readAndSaveCSVFile();
		Year year = dateHelper.getYear(yr);
		List<BigDecimal> closePriceList = stockPriceDAO.getClosePriceBetweenDates(year.atDay(1),
				year.atMonth(12).atEndOfMonth());
		BigDecimal mean = getAvegareClosePrice(closePriceList);
		yearAvgClosePriceMap.put(yr, mean);
		return yearAvgClosePriceMap;

	}

	/**
	 * This function calculate the average close price of the list.
	 * 
	 * @param closePriceList
	 * @return
	 */
	private BigDecimal getAvegareClosePrice(List<BigDecimal> closePriceList) {
		BigDecimal[] totalWithCount = closePriceList.stream().filter(bd -> bd != null)
				.map(bd -> new BigDecimal[] { bd, BigDecimal.ONE })
				.reduce((a, b) -> new BigDecimal[] { a[0].add(b[0]), a[1].add(BigDecimal.ONE) }).get();
		BigDecimal mean = totalWithCount[0].divide(totalWithCount[1], 10, RoundingMode.HALF_UP);
		return mean;
	}

	/**
	 * This function reads the data of csv file and save the data in DB.
	 * 
	 * @throws IOException
	 */
	private void readAndSaveCSVFile() throws IOException {
		LOGGER.info("Entering service method to read and save the CSV file");
		List<StockPrice> stockPriceList = cSVReader.getDataFromCSVFile();
		stockPriceDAO.save(stockPriceList);
	}

}
