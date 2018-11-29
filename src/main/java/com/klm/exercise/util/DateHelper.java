package com.klm.exercise.util;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class DateHelper {

	/**
	 * This function return the Year obj for a particular year.
	 * 
	 * @param yr
	 * @return
	 */
	public Year getYear(String yr) {
		int intYear = Integer.parseInt(yr);
		Year year = Year.of(intYear);
		return year;
	}

	/**
	 * This function return the YearMonth obj for a particular year and month.
	 * 
	 * @param yr
	 * @param mon
	 * @return
	 */
	public YearMonth getYearMonth(String yr, String mon) {
		int intYear = Integer.parseInt(yr);
		int intMon = Integer.parseInt(mon);
		YearMonth yearMonth = YearMonth.of(intYear, intMon);
		return yearMonth;
	}

	/**
	 * This function return the LocalDate obj for a particular year, month and
	 * day.
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public LocalDate getDate(String year, String month, String day) {
		int intYear = Integer.parseInt(year);
		int intMon = Integer.parseInt(month);
		int intDay = Integer.parseInt(day);
		return LocalDate.of(intYear, intMon, intDay);
	}

	/**
	 * This function return the LocalDate obj for a String having format
	 * dd-MM-yyyy
	 * 
	 * @param dateStr
	 * @return
	 */
	public LocalDate getFormattedDate(String dateStr) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
		return LocalDate.parse(dateStr, formatter);
	}

}
