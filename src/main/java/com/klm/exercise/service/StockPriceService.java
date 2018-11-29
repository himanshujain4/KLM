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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klm.exercise.dao.StockPriceDAO;
import com.klm.exercise.model.StockPrice;
import com.klm.exercise.util.CSVReader;
import com.klm.exercise.util.DateHelper;

@Service
public class StockPriceService {
	
	@Autowired
	private CSVReader cSVReader;

	@Autowired
	private StockPriceDAO stockPriceDAO;

	@Autowired
	private DateHelper dateHelper;

	/**
	 * This function returns the close price of stock for entire csv file data.
	 * Returns a map having date as key and close price as value.
	 * 
	 * @return
	 * @throws IOException
	 */
	public Map<String, BigDecimal> getClosePriceForEntireTimeSpanOfFile() throws IOException {
		readAndSaveCSVFile();
		Map<String, BigDecimal> dateClosePriceMap = new HashMap<>();
		List<StockPrice> stockPriceList = stockPriceDAO.getClosePriceForEntireTimeSpanOfFile();
		for (StockPrice stockPrice : stockPriceList) {
			dateClosePriceMap.put(stockPrice.getDate().toString(), stockPrice.getClosePrice());
		}
		return dateClosePriceMap;

	}

	/**
	 * This function returns the close price of stock within two
	 * dates. Returns a map having date as key and close price as value.
	 * 
	 * @param fromDateStr
	 * @param toDateStr
	 * @return
	 * @throws IOException
	 */
	public Map<String, BigDecimal> getClosePriceBetweenDates(String fromDateStr, String toDateStr) throws IOException {
		Map<String, BigDecimal> dateClosePriceMap = new HashMap<>();
		readAndSaveCSVFile();
		LocalDate fromDate = dateHelper.getFormattedDate(fromDateStr);
		LocalDate toDate = dateHelper.getFormattedDate(toDateStr);

		List<StockPrice> stockPriceList = stockPriceDAO.getStockPriceBetweenDates(fromDate, toDate);
		for (StockPrice stockPrice : stockPriceList) {
			dateClosePriceMap.put(stockPrice.getDate().toString(), stockPrice.getClosePrice());
		}
		return dateClosePriceMap;

	}
	
	/**
	 * This function returns the average close price of stock within a year.
	 * Returns a map having year as key and avg close price as value.
	 * 
	 * @param year
	 * @return
	 * @throws IOException
	 */
	public Map<String, BigDecimal> getAverageClosePriceWithinYear(String yr) throws IOException {
		Map<String, BigDecimal> yearAvgClosePriceMap = new HashMap<>();
		readAndSaveCSVFile();
		Year year = dateHelper.getYear(yr);

		List<BigDecimal> closePriceList = stockPriceDAO.getClosePriceBetweenDates(year.atDay(1), year.atMonth(12).atEndOfMonth());

		BigDecimal[] totalWithCount = closePriceList.stream().filter(bd -> bd != null)
				.map(bd -> new BigDecimal[] { bd, BigDecimal.ONE })
				.reduce((a, b) -> new BigDecimal[] { a[0].add(b[0]), a[1].add(BigDecimal.ONE) }).get();
		BigDecimal mean = totalWithCount[0].divide(totalWithCount[1], 10, RoundingMode.HALF_UP);
		yearAvgClosePriceMap.put(yr, mean);
		return yearAvgClosePriceMap;

	}
	
	/**
	 * This function returns the average close price of stock within a month of
	 * a year. Returns a map having year/month as key and avg close price as
	 * value.
	 * 
	 * @param year
	 * @param month
	 * @return
	 * @throws IOException
	 */
	public Map<String, BigDecimal> getAverageClosePriceWithinMonthOfYear(String year, String month) throws IOException {
		Map<String, BigDecimal> yearMonthAvgClosePriceMap = new HashMap<>();
		readAndSaveCSVFile();
		YearMonth yearMonth = dateHelper.getYearMonth(year, month);

		List<BigDecimal> closePriceList = stockPriceDAO.getClosePriceBetweenDates(yearMonth.atDay(1), yearMonth.atEndOfMonth());

		BigDecimal[] totalWithCount = closePriceList.stream().filter(bd -> bd != null)
				.map(bd -> new BigDecimal[] { bd, BigDecimal.ONE })
				.reduce((a, b) -> new BigDecimal[] { a[0].add(b[0]), a[1].add(BigDecimal.ONE) }).get();
		BigDecimal mean = totalWithCount[0].divide(totalWithCount[1], 10, RoundingMode.HALF_UP);
		yearMonthAvgClosePriceMap.put(year + "/" + month, mean);
		return yearMonthAvgClosePriceMap;
	}
	
	/**
	 * This function return the close price of stock for a particular date.
	 * Returns a map having year/month/day as key and close price as value.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 * @throws IOException
	 */
	public Map<String, BigDecimal> getClosePriceForDate(String year, String month, String day) throws IOException {
		Map<String, BigDecimal> dateClosePriceMap = new HashMap<>();
		readAndSaveCSVFile();
		LocalDate date = dateHelper.getDate(year, month, day);

		BigDecimal requiredStockPrice = stockPriceDAO.getClosePriceForDate(date);

		dateClosePriceMap.put(year + "/" + month + "/" + day, requiredStockPrice);
		return dateClosePriceMap;
	}
	
	/**
	 * This function reads the data of csv file and save the data in DB.
	 * 
	 * @throws IOException
	 */
	public void readAndSaveCSVFile() throws IOException {
		List<StockPrice> stockPriceList = cSVReader.getDataFromCSVFile();
		stockPriceDAO.save(stockPriceList);
	}

}
