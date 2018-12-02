package com.klm.exercise.controller;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.klm.exercise.exception.ExceptionResponse;
import com.klm.exercise.exception.RecordNotFoundException;
import com.klm.exercise.util.CSVReader;
import com.klm.exercise.util.Constants;

@ControllerAdvice
@RestController
public class CustomizedExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomizedExceptionHandler.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now().toString(), ex.getMessage() + Constants.UNHANDLED_EXCEPTION_MESSAGE,
				request.getDescription(false));
		LOGGER.info(Constants.UNHANDLED_EXCEPTION_MESSAGE, ex);
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(RecordNotFoundException.class)
	public final ResponseEntity<Object> handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now().toString(), ex.getMessage() + Constants.RECORD_NOT_FOUND_EXCEPTION_MESSAGE,
				request.getDescription(false));
		LOGGER.info(Constants.RECORD_NOT_FOUND_EXCEPTION_MESSAGE, ex);
		return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(NumberFormatException.class)
	public final ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now().toString(), ex.getMessage() + Constants.NUMBER_FORMAT_EXCEPTION_MESSAGE,
				request.getDescription(false));
		LOGGER.info(Constants.NUMBER_FORMAT_EXCEPTION_MESSAGE, ex);
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(DateTimeException.class)
	public final ResponseEntity<Object> handleDateTimeException(DateTimeException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now().toString(), ex.getMessage() + Constants.DATE_TIME_EXCEPTION_MESSAGE,
				request.getDescription(false));
		LOGGER.info(Constants.DATE_TIME_EXCEPTION_MESSAGE, ex);
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(IllegalArgumentException.class)
	public final ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now().toString(), ex.getMessage() + Constants.ILLEGAL_ARGU_EXCEPTION_MESSAGE,
				request.getDescription(false));
		LOGGER.info(Constants.ILLEGAL_ARGU_EXCEPTION_MESSAGE, ex);
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(IOException.class)
	public final ResponseEntity<Object> handleIOException(IOException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now().toString(), ex.getMessage() + Constants.IO_EXCEPTION_MESSAGE,
				request.getDescription(false));
		LOGGER.info(Constants.IO_EXCEPTION_MESSAGE, ex);
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(HibernateException.class)
	public final ResponseEntity<Object> handleHibernateException(HibernateException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now().toString(), ex.getMessage() + Constants.HIBERNATE_EXCEPTION_MESSAGE,
				request.getDescription(false));
		LOGGER.info(Constants.HIBERNATE_EXCEPTION_MESSAGE, ex);
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ExceptionHandler(ArithmeticException.class)
	public final ResponseEntity<Object> handleArithmeticException(ArithmeticException ex, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now().toString(), ex.getMessage() + Constants.ARTHIMATIC_EXCEPTION_MESSAGE,
				request.getDescription(false));
		LOGGER.info(Constants.ARTHIMATIC_EXCEPTION_MESSAGE, ex);
		return new ResponseEntity(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDate.now().toString(), "Validation Failed",
				ex.getBindingResult().toString());
		return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
	
	
	 
	 
	
	
	
}
