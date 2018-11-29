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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.klm.exercise.model.StockPrice;
import com.klm.exercise.util.Constants;

@Repository
@Transactional
public class StockPriceDAO {

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
		List<BigDecimal> list = new ArrayList<>();
		Session session = null;
		if (null != sessionFactory) {
			session = sessionFactory.getCurrentSession();
		}
		if (null != session) {
			Criteria criteria = session.createCriteria(StockPrice.class);
			criteria.setProjection(Projections.property(Constants.CLOSE_PRICE_ATTRIBUTE));
			criteria.add(Restrictions.between(Constants.DATE_ATTRIBUTE, fromDate, toDate));
			list = criteria.list();
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
		Session session = null;
		if (null != sessionFactory) {
			session = sessionFactory.getCurrentSession();
		}
		if (null != session) {
			Criteria criteria = session.createCriteria(StockPrice.class);
			criteria.setProjection(Projections.property(Constants.CLOSE_PRICE_ATTRIBUTE));
			criteria.add(Restrictions.eq(Constants.DATE_ATTRIBUTE, date));
			List<BigDecimal> list = criteria.list();
			if (CollectionUtils.isNotEmpty(list)) {
				return (BigDecimal) list.get(0);
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
		List<StockPrice> list = new ArrayList<>();
		Session session = null;
		if (null != sessionFactory) {
			session = sessionFactory.getCurrentSession();
		}
		if (null != session) {
			Criteria criteria = session.createCriteria(StockPrice.class);
			list = criteria.list();
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
		List<StockPrice> list = new ArrayList<>();
		Session session = null;
		if (null != sessionFactory) {
			session = sessionFactory.getCurrentSession();
		}
		if (null != session) {
			Criteria criteria = session.createCriteria(StockPrice.class);
			criteria.add(Restrictions.between(Constants.DATE_ATTRIBUTE, fromDate, toDate));
			list = criteria.list();
		}
		return list;
	}
	
	/**
	 * This function save the list of StockPrice into DB.
	 * 
	 * @param stockPriceList
	 */
	public void save(List<StockPrice> stockPriceList) {
		Session session = null;
		if (null != sessionFactory) {
			session = sessionFactory.getCurrentSession();
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
		}
	}

}
