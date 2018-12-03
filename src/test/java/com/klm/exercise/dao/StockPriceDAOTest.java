package com.klm.exercise.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import com.klm.exercise.model.StockPrice;
import com.klm.exercise.util.Constants;

@RunWith(MockitoJUnitRunner.class)
//@SpringBootTest
public class StockPriceDAOTest {

	@Mock
	private SessionFactory sessionFactory ;

	@Mock
	private Session session;

	@Mock
	private Criteria criteria;

	@Mock
	private Transaction transaction;

	@InjectMocks
	private StockPriceDAO stockPriceDAO;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetClosePriceBetweenDates() {
		LocalDate fromDate = LocalDate.of(2001, 01, 05);
		LocalDate toDate = LocalDate.of(2002, 12, 10);

		List<BigDecimal> list = new ArrayList<>();
		list.add(new BigDecimal("1"));
		list.add(new BigDecimal("2"));

		when(sessionFactory.openSession()).thenReturn(session);
		when(session.createCriteria(StockPrice.class)).thenReturn(criteria);
		when(criteria.setProjection(Projections.property(Constants.CLOSE_PRICE_ATTRIBUTE))).thenReturn(criteria);
		when(criteria.add(Restrictions.between(Constants.DATE_ATTRIBUTE, fromDate, toDate))).thenReturn(criteria);
		when(criteria.list()).thenReturn(list);

		list = stockPriceDAO.getClosePriceBetweenDates(fromDate, toDate);
		assertEquals(2, list.size());
		assertTrue(new BigDecimal("1").equals(list.get(0)));
	}
	
	@Test
	public void testGetClosePriceForDate() {
		LocalDate date = LocalDate.of(2001, 01, 05);
		List<BigDecimal> list = new ArrayList<>();
		list.add(new BigDecimal("2"));
		list.add(new BigDecimal("1"));

		when(sessionFactory.openSession()).thenReturn(session);
		when(session.createCriteria(StockPrice.class)).thenReturn(criteria);
		when(criteria.setProjection(Projections.property(Constants.CLOSE_PRICE_ATTRIBUTE))).thenReturn(criteria);
		when(criteria.add(Restrictions.eq(Constants.DATE_ATTRIBUTE, date))).thenReturn(criteria);
		when(criteria.list()).thenReturn(list);

		BigDecimal bd = stockPriceDAO.getClosePriceForDate(date);
		assertTrue(new BigDecimal("2").equals(bd));
	}
	
	@Test
	public void testSave() {
		List<StockPrice> list = new ArrayList<>();
		StockPrice stockPrice1 = new StockPrice();
		stockPrice1.setClosePrice(new BigDecimal("2.04"));
		
		StockPrice stockPrice2 = new StockPrice();
		stockPrice2.setClosePrice(new BigDecimal("5"));
		
		list.add(stockPrice1);
		list.add(stockPrice2);
		
		when(sessionFactory.openSession()).thenReturn(session);
		when(session.beginTransaction()).thenReturn(transaction);
		when(session.save(stockPrice2)).thenReturn("2");
		stockPriceDAO.save(list);
	}
	
	@Test
	public void testGetStockPriceBetweenDates() {
		LocalDate fromDate = LocalDate.of(2001, 01, 05);
		LocalDate toDate = LocalDate.of(2002, 12, 10);
		
		List<StockPrice> list = new ArrayList<>();
		StockPrice stockPrice1 = new StockPrice();
		stockPrice1.setClosePrice(new BigDecimal("2.04"));
		
		StockPrice stockPrice2 = new StockPrice();
		stockPrice2.setClosePrice(new BigDecimal("5"));
		
		list.add(stockPrice1);
		list.add(stockPrice2);
		
		when(sessionFactory.openSession()).thenReturn(session);
		when(session.createCriteria(StockPrice.class)).thenReturn(criteria);
		when(criteria.add(Restrictions.between(Constants.DATE_ATTRIBUTE, fromDate, toDate))).thenReturn(criteria);
		when(criteria.list()).thenReturn(list);

		list = stockPriceDAO.getStockPriceBetweenDates(fromDate, toDate);
		assertEquals(2, list.size());
		assertTrue(new BigDecimal("2.04").equals(list.get(0).getClosePrice()));
	}
	
}
