package com.klm.exercise.util;

public class Constants {
	
	/** Date format for request */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	/** Close Price attribute for StockPrice Model */
	public static final String CLOSE_PRICE_ATTRIBUTE = "closePrice";
	
	/** Date attribute for StockPrice Model */
	public static final String DATE_ATTRIBUTE = "date";

	/** RecordNotFoundException message */
	public static final String RECORD_NOT_FOUND_FOR_MENTIONED_DATA = "Record not found for mentioned data";

	/** Exception message */
	public static final String UNHANDLED_EXCEPTION_MESSAGE = "- Some unhandled exception occured. Please try later.";

	/** RecordNotFoundException message */
	public static final String RECORD_NOT_FOUND_EXCEPTION_MESSAGE = "- Record not found for mentioned data. Please provide another data.";

	/** NumberFormatException message */
	public static final String NUMBER_FORMAT_EXCEPTION_MESSAGE = "- Please provide the valid numbers for date in the request.";

	/** DateTimeException message */
	public static final String DATE_TIME_EXCEPTION_MESSAGE = "- Please provide valid formate for date/time.";

	/** IllegalArgumentException message */
	public static final String ILLEGAL_ARGU_EXCEPTION_MESSAGE = "- Some illegal arguments in the request. Please try again.";

	/** IOException message */
	public static final String IO_EXCEPTION_MESSAGE = "- Unable to read the csv file.";

	/** HibernateException message */
	public static final String HIBERNATE_EXCEPTION_MESSAGE = "- Some exception occured while saving/fetching the data. Please try later.";

	/** ArithmeticException message */
	public static final String ARTHIMATIC_EXCEPTION_MESSAGE = "- Arthimatic exception occured. There may be some bad data in the csv file.";

	/**String dash	 */
	public static final String DASH = "-";
}
