package com.klm.exercise.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.klm.exercise.dao.StockPriceDAO;
import com.klm.exercise.model.StockPrice;
import com.klm.exercise.util.CSVReader;
import com.klm.exercise.util.DateHelper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StockPriceServiceTest {
	@Mock
	private CSVReader cSVReader;

	@Mock
	private StockPriceDAO stockPriceDAO;

	@Mock
	private DateHelper dateHelper;

	@InjectMocks
	private StockPriceService stockPriceService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetClosePriceForEntireTimeSpanOfFile() throws IOException {
		LocalDate date1 = LocalDate.of(2001, 01, 05);
		LocalDate date2 = LocalDate.of(2002, 12, 10);

		List<StockPrice> list = new ArrayList<>();
		StockPrice stockPrice1 = new StockPrice();
		stockPrice1.setDate(date1);
		stockPrice1.setClosePrice(new BigDecimal("2.04"));
		StockPrice stockPrice2 = new StockPrice();
		stockPrice2.setDate(date2);
		stockPrice2.setClosePrice(new BigDecimal("5"));
		list.add(stockPrice1);
		list.add(stockPrice2);

		when(stockPriceDAO.getClosePriceForEntireTimeSpanOfFile()).thenReturn(list);

		Map<String, BigDecimal> dateClosePriceMap = stockPriceService.getClosePriceForEntireTimeSpanOfFile();
		assertEquals(2, dateClosePriceMap.size());
		assertEquals(0, dateClosePriceMap.values().iterator().next().compareTo(new BigDecimal("5")));
	}

	@Test
	public void testGetClosePriceBetweenDates() throws IOException {
		String dateStr1 = "05-01-2001";
		String dateStr2 = "10-12-2002";
		LocalDate date1 = LocalDate.of(2001, 01, 05);
		LocalDate date2 = LocalDate.of(2002, 12, 10);

		List<StockPrice> list = new ArrayList<>();
		StockPrice stockPrice1 = new StockPrice();
		stockPrice1.setDate(date1);
		stockPrice1.setClosePrice(new BigDecimal("2.04"));
		StockPrice stockPrice2 = new StockPrice();
		stockPrice2.setDate(date2);
		stockPrice2.setClosePrice(new BigDecimal("5"));
		list.add(stockPrice1);
		list.add(stockPrice2);

		when(dateHelper.getFormattedDate("05-01-2001")).thenReturn(date1);
		when(dateHelper.getFormattedDate("10-12-2002")).thenReturn(date2);
		when(stockPriceDAO.getStockPriceBetweenDates(date1, date2)).thenReturn(list);

		Map<String, BigDecimal> dateClosePriceMap = stockPriceService.getClosePriceBetweenDates(dateStr1, dateStr2);
		assertEquals(2, dateClosePriceMap.size());
		assertEquals(0, dateClosePriceMap.values().iterator().next().compareTo(new BigDecimal("5")));
	}

	@Test
	public void testGetAverageClosePriceWithinYear() throws IOException {
		String yrStr = "2001";
		Year year = Year.of(2001);

		List<BigDecimal> list = new ArrayList<>();
		list.add(new BigDecimal("1"));
		list.add(new BigDecimal("2"));

		when(dateHelper.getYear(yrStr)).thenReturn(year);
		when(stockPriceDAO.getClosePriceBetweenDates(year.atDay(1), year.atMonth(12).atEndOfMonth())).thenReturn(list);

		Map<String, BigDecimal> dateClosePriceMap = stockPriceService.getAverageClosePriceWithinYear(yrStr);
		assertEquals(1, dateClosePriceMap.size());
		assertEquals(0, dateClosePriceMap.values().iterator().next().compareTo(new BigDecimal("1.5")));
	}

	@Test
	public void testGetAverageClosePriceWithinMonthOfYear() throws IOException {
		String yrStr = "2001";
		String monStr = "10";
		YearMonth yearMonth = YearMonth.of(2001, 10);

		List<BigDecimal> list = new ArrayList<>();
		list.add(new BigDecimal("1"));
		list.add(new BigDecimal("2"));

		when(dateHelper.getYearMonth(yrStr, monStr)).thenReturn(yearMonth);
		when(stockPriceDAO.getClosePriceBetweenDates(yearMonth.atDay(1), yearMonth.atEndOfMonth())).thenReturn(list);

		Map<String, BigDecimal> dateClosePriceMap = stockPriceService.getAverageClosePriceWithinMonthOfYear(yrStr,
				monStr);
		assertEquals(1, dateClosePriceMap.size());
		assertEquals(0, dateClosePriceMap.values().iterator().next().compareTo(new BigDecimal("1.5")));
	}

	@Test
	public void testGetClosePriceForDate() throws IOException {
		String yrStr = "2001";
		String monStr = "10";
		String dayStr = "25";
		LocalDate date = LocalDate.of(2001, 10, 25);

		BigDecimal bd = new BigDecimal("1");

		when(dateHelper.getDate(yrStr, monStr, dayStr)).thenReturn(date);
		when(stockPriceDAO.getClosePriceForDate(date)).thenReturn(bd);

		Map<String, BigDecimal> dateClosePriceMap = stockPriceService.getClosePriceForDate(yrStr, monStr, dayStr);
		assertEquals(1, dateClosePriceMap.size());
		assertEquals(0, dateClosePriceMap.values().iterator().next().compareTo(new BigDecimal("1")));
	}
}
