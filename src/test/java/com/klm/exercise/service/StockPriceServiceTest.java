package com.klm.exercise.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

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
import org.mockito.runners.MockitoJUnitRunner;

import com.klm.exercise.dao.StockPriceDAO;
import com.klm.exercise.model.StockPrice;
import com.klm.exercise.util.CSVReader;
import com.klm.exercise.util.DateHelper;

@RunWith(MockitoJUnitRunner.class)
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
	public void testGetCloseRateOverTime_Year() throws Exception {
		String yrStr = "2016";
		Year year = Year.of(2016);

		List<StockPrice> list = new ArrayList<>();
		StockPrice stockPrice1 = new StockPrice();
		stockPrice1.setDate(year.atDay(1));
		stockPrice1.setClosePrice(new BigDecimal("2.04"));
		StockPrice stockPrice2 = new StockPrice();
		stockPrice2.setDate(year.atDay(2));
		stockPrice2.setClosePrice(new BigDecimal("5"));
		list.add(stockPrice1);
		list.add(stockPrice2);

		when(dateHelper.getYear(yrStr)).thenReturn(year);
		when(stockPriceDAO.getStockPriceBetweenDates(year.atDay(1), year.atMonth(12).atEndOfMonth())).thenReturn(list);

		Map<String, BigDecimal> dateClosePriceMap = stockPriceService.getCloseRateOverTime(yrStr, null, "");
		assertEquals(2, dateClosePriceMap.size());
		assertEquals(0, dateClosePriceMap.values().iterator().next().compareTo(new BigDecimal("5")));
	}

	@Test
	public void testGetCloseRateOverTime_YearMonth() throws Exception {
		String yrStr = "2016";
		String monStr = "04";
		YearMonth yearMonth = YearMonth.of(2016, 04);

		List<StockPrice> list = new ArrayList<>();
		StockPrice stockPrice1 = new StockPrice();
		stockPrice1.setDate(yearMonth.atDay(1));
		stockPrice1.setClosePrice(new BigDecimal("2.04"));
		StockPrice stockPrice2 = new StockPrice();
		stockPrice2.setDate(yearMonth.atDay(2));
		stockPrice2.setClosePrice(new BigDecimal("5"));
		list.add(stockPrice1);
		list.add(stockPrice2);

		when(dateHelper.getYearMonth(yrStr, monStr)).thenReturn(yearMonth);
		when(stockPriceDAO.getStockPriceBetweenDates(yearMonth.atDay(1), yearMonth.atEndOfMonth())).thenReturn(list);

		Map<String, BigDecimal> dateClosePriceMap = stockPriceService.getCloseRateOverTime(yrStr, monStr, null);
		assertEquals(2, dateClosePriceMap.size());
		assertEquals(0, dateClosePriceMap.values().iterator().next().compareTo(new BigDecimal("5")));
	}

	@Test
	public void testGetCloseRateOverTime_Date() throws Exception {
		String yrStr = "2016";
		String monStr = "04";
		String dayStr = "04";
		LocalDate date = LocalDate.of(2016, 04, 04);

		BigDecimal bd = new BigDecimal("5");
		when(dateHelper.getDate(yrStr, monStr, dayStr)).thenReturn(date);
		when(stockPriceDAO.getClosePriceForDate(date)).thenReturn(bd);

		Map<String, BigDecimal> dateClosePriceMap = stockPriceService.getCloseRateOverTime(yrStr, monStr, dayStr);
		assertEquals(1, dateClosePriceMap.size());
		assertEquals(0, dateClosePriceMap.values().iterator().next().compareTo(new BigDecimal("5")));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetCloseRateOverTime_Exception() throws Exception {
		Map<String, BigDecimal> dateClosePriceMap = stockPriceService.getCloseRateOverTime(null, null, null);
	}

	@Test
	public void testGetAverageCloseRateOverPeriod_Year() throws Exception {
		String yrStr = "2016";
		String monStr = "04";
		YearMonth yearMonth = YearMonth.of(2016, 04);

		List<BigDecimal> list = new ArrayList<>();
		list.add(new BigDecimal("1"));
		list.add(new BigDecimal("2"));

		when(dateHelper.getYearMonth(yrStr, monStr)).thenReturn(yearMonth);
		when(stockPriceDAO.getClosePriceBetweenDates(yearMonth.atDay(1), yearMonth.atEndOfMonth())).thenReturn(list);

		Map<String, BigDecimal> dateClosePriceMap = stockPriceService.getAverageCloseRateOverPeriod(yrStr, monStr,
				null);
		assertEquals(1, dateClosePriceMap.size());
		assertEquals(0, dateClosePriceMap.values().iterator().next().compareTo(new BigDecimal("1.5")));

	}

	@Test
	public void testGetAverageCloseRateOverPeriod_YearMonth() throws Exception {
		String yrStr = "2016";
		String monStr = "04";
		YearMonth yearMonth = YearMonth.of(2016, 04);

		List<BigDecimal> list = new ArrayList<>();
		list.add(new BigDecimal("1"));
		list.add(new BigDecimal("2"));

		when(dateHelper.getYearMonth(yrStr, monStr)).thenReturn(yearMonth);
		when(stockPriceDAO.getClosePriceBetweenDates(yearMonth.atDay(1), yearMonth.atEndOfMonth())).thenReturn(list);

		Map<String, BigDecimal> dateClosePriceMap = stockPriceService.getAverageCloseRateOverPeriod(yrStr, monStr,
				null);
		assertEquals(1, dateClosePriceMap.size());
		assertEquals(0, dateClosePriceMap.values().iterator().next().compareTo(new BigDecimal("1.5")));

	}

	@Test
	public void testGetAverageCloseRateOverPeriod_Date() throws Exception {
		String yrStr = "2016";
		String monStr = "04";
		String dayStr = "04";
		LocalDate date = LocalDate.of(2016, 04, 04);

		BigDecimal bd = new BigDecimal("5");
		when(dateHelper.getDate(yrStr, monStr, dayStr)).thenReturn(date);
		when(stockPriceDAO.getClosePriceForDate(date)).thenReturn(bd);

		Map<String, BigDecimal> dateClosePriceMap = stockPriceService.getAverageCloseRateOverPeriod(yrStr, monStr,
				dayStr);
		assertEquals(1, dateClosePriceMap.size());
		assertEquals(0, dateClosePriceMap.values().iterator().next().compareTo(new BigDecimal("5")));

	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetAverageCloseRateOverPeriod_Exception() throws Exception {
		Map<String, BigDecimal> dateClosePriceMap = stockPriceService.getAverageCloseRateOverPeriod(null, null, null);
	}
}
