package com.klm.exercise.exception;

public class ExceptionResponse {
	private String date;
	private String message;
	private String details;

	public ExceptionResponse(String date, String message, String details) {
		super();
		this.date = date;
		this.message = message;
		this.details = details;
	}

	public String getTimestamp() {
		return date;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}

}
