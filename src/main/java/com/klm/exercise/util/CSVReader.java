package com.klm.exercise.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.klm.exercise.model.StockPrice;

/**
 * This class reads the data of csv file and wraps the data around StockPrice.
 *
 */
@Component
public class CSVReader {
	private static final Logger LOGGER = LoggerFactory.getLogger(CSVReader.class);
	
	@Value("classpath:KLM.csv")
	private Resource res;

	/**
	 * This function reads the data of csv file from class path and wraps the
	 * data around StockPrice. Return a list of StockPrice object.
	 * 
	 * @return
	 * @throws IOException
	 */
	public List<StockPrice> getDataFromCSVFile() throws IOException {
		LOGGER.info("Entering method to read the csv file");
		List<StockPrice> stockPriceList = new ArrayList<StockPrice>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
		Reader in = new FileReader(res.getFile());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		for (CSVRecord record : records) {
			StockPrice stockPrice = new StockPrice();
			stockPrice.setDate(LocalDate.parse(record.get(CSVHeader.DATE.getHeader()), formatter));
			stockPrice.setOpenPrice(new BigDecimal(record.get(CSVHeader.OPEN.getHeader())));
			stockPrice.setHighPrice(new BigDecimal(record.get(CSVHeader.HIGH.getHeader())));
			stockPrice.setLowPrice(new BigDecimal(record.get(CSVHeader.LOW.getHeader())));
			stockPrice.setClosePrice(new BigDecimal(record.get(CSVHeader.CLOSE.getHeader())));
			stockPrice.setAdjClosePrice(new BigDecimal(record.get(CSVHeader.ADJCLOSE.getHeader())));
			stockPrice.setVolume(Integer.parseInt(record.get(CSVHeader.VOLUME.getHeader())));
			stockPriceList.add(stockPrice);
		}
		in.close();
		return stockPriceList;
	}

}
