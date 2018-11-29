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
		List<StockPrice> stockPriceList = new ArrayList<StockPrice>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);
		Reader in = new FileReader(res.getFile());
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
		for (CSVRecord record : records) {
			StockPrice stockPriceModel = new StockPrice();
			stockPriceModel.setDate(LocalDate.parse(record.get(CSVHeader.DATE), formatter));
			stockPriceModel.setOpenPrice(new BigDecimal(record.get(CSVHeader.OPEN)));
			stockPriceModel.setHighPrice(new BigDecimal(record.get(CSVHeader.HIGH)));
			stockPriceModel.setLowPrice(new BigDecimal(record.get(CSVHeader.LOW)));
			stockPriceModel.setClosePrice(new BigDecimal(record.get(CSVHeader.CLOSE)));
			stockPriceModel.setAdjClosePrice(new BigDecimal(record.get(CSVHeader.ADJCLOSE)));
			stockPriceModel.setVolume(Integer.parseInt(record.get(CSVHeader.VOLUME)));
			stockPriceList.add(stockPriceModel);
		}
		return stockPriceList;
	}

}
