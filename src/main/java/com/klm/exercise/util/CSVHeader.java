package com.klm.exercise.util;

public enum CSVHeader {

	/**
	 * Date Header
	 */
	DATE("Date"),

	/**
	 * Open Price Header
	 */
	OPEN("Open"),

	/**
	 * High Price Header
	 */
	HIGH("High"),

	/**
	 * Low Price Header
	 */
	LOW("Low"),

	/**
	 * Close Price Header
	 */
	CLOSE("Close"),

	/**
	 * Adj. Close Price Header
	 */
	ADJCLOSE("Adj Close"),

	/**
	 * Volume Header
	 */
	VOLUME("Volume");

	private String header;

	CSVHeader(String header) {
		this.header = header;
	}

	public String getHeader() {
		return header;
	}

}
