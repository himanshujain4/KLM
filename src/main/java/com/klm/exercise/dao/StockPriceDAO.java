package com.klm.exercise.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.klm.exercise.exception.RecordNotFoundException;
import com.klm.exercise.model.StockPrice;
import com.klm.exercise.service.StockPriceService;
import com.klm.exercise.util.Constants;

@Repository
@Transactional
public class StockPriceDAO {
	private static final Logger LOGGER = LoggerFactory.getLogger(StockPriceDAO.class);

	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * This function returns the list of close price of stock within two dates.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BigDecimal> getClosePriceBetweenDates(LocalDate fromDate, LocalDate toDate) {
		LOGGER.info("Entering dao method to get the close price of stock from " + fromDate + " to" + toDate);
		List<BigDecimal> list = new ArrayList<>();
		Session session = null;
		if (null != sessionFactory) {
			session = sessionFactory.openSession();
		}
		if (null != session) {
			Criteria criteria = session.createCriteria(StockPrice.class);
			criteria.setProjection(Projections.property(Constants.CLOSE_PRICE_ATTRIBUTE));
			criteria.add(Restrictions.between(Constants.DATE_ATTRIBUTE, fromDate, toDate));
			list = criteria.list();
			session.close();
			if (CollectionUtils.isEmpty(list)) {
				throw new RecordNotFoundException(Constants.RECORD_NOT_FOUND_FOR_MENTIONED_DATA);
			}
		}
		return list;
	}

	/**
	 * This function returns the close price of stock for a particular date.
	 * 
	 * @param date
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BigDecimal getClosePriceForDate(LocalDate date) {
		LOGGER.info("Entering dao method to get the close price of stock for date " + date);
		Session session = null;
		if (null != sessionFactory) {
			session = sessionFactory.openSession();
		}
		if (null != session) {
			Criteria criteria = session.createCriteria(StockPrice.class);
			criteria.setProjection(Projections.property(Constants.CLOSE_PRICE_ATTRIBUTE));
			criteria.add(Restrictions.eq(Constants.DATE_ATTRIBUTE, date));
			List<BigDecimal> list = criteria.list();
			session.close();
			if (CollectionUtils.isNotEmpty(list)) {
				return (BigDecimal) list.get(0);
			} else {
				throw new RecordNotFoundException(Constants.RECORD_NOT_FOUND_FOR_MENTIONED_DATA);
			}
		}
		return null;
	}

	/**
	 * This function returns the list of stock price object for entire csv file
	 * data.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StockPrice> getClosePriceForEntireTimeSpanOfFile() {
		LOGGER.info("Entering dao method to get the close price of stock for entire csv file data");
		List<StockPrice> list = new ArrayList<>();
		Session session = null;
		if (null != sessionFactory) {
			session = sessionFactory.openSession();
		}
		if (null != session) {
			Criteria criteria = session.createCriteria(StockPrice.class);
			list = criteria.list();
			session.close();
			if (CollectionUtils.isEmpty(list)) {
				throw new RecordNotFoundException(Constants.RECORD_NOT_FOUND_FOR_MENTIONED_DATA);
			}
		}
		return list;
	}

	/**
	 * This function returns the list of stock price object between two dates.
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StockPrice> getStockPriceBetweenDates(LocalDate fromDate, LocalDate toDate) {
		LOGGER.info("Entering dao method to get the stock price from " + fromDate + " to" + toDate);
		List<StockPrice> list = new ArrayList<>();
		Session session = null;
		if (null != sessionFactory) {
			session = sessionFactory.openSession();
		}
		if (null != session) {
			Criteria criteria = session.createCriteria(StockPrice.class);
			criteria.add(Restrictions.between(Constants.DATE_ATTRIBUTE, fromDate, toDate));
			list = criteria.list();
			session.close();
			if (CollectionUtils.isEmpty(list)) {
				throw new RecordNotFoundException(Constants.RECORD_NOT_FOUND_FOR_MENTIONED_DATA);
			}
		}
		return list;
	}
	
	/**
	 * This function save the list of StockPrice into DB.
	 * 
	 * @param stockPriceList
	 */
	public void save(List<StockPrice> stockPriceList) {
		LOGGER.info("Entering dao method to save the csv data iinto DB");
		Session session = null;
		if (null != sessionFactory) {
			session = sessionFactory.openSession();
		}
		if (null != session) {
			Transaction transaction = session.beginTransaction();
			for (int i = 0; i < stockPriceList.size(); i++) {
				session.save(stockPriceList.get(i));
				if (i % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.close();
		}
	}

}
