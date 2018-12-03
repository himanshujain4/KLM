package com.klm.exercise.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.klm.exercise.service.StockPriceService;

@RunWith(SpringRunner.class)
@SpringBootTest()
@WebAppConfiguration
public class StockPriceControllerTest {

	private static MediaType CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Mock
	private StockPriceService stockPriceService;

	@Before
	public void init() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void testGetCloseRateOverTime_Year() throws Exception {
		mockMvc.perform(get("/getCloseRateOverTime/time?year=2016")).andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$.2016-07-08", Matchers.is(13.09000000)))
				.andExpect(jsonPath("$.2016-11-22", Matchers.is(11.89000000)));
	}

	@Test
	public void testGetCloseRateOverTime_YearMonth() throws Exception {
		mockMvc.perform(get("/getCloseRateOverTime/time?year=2016&month=04")).andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$.2016-04-22", Matchers.is(13.61000000)))
				.andExpect(jsonPath("$.2016-04-19", Matchers.is(13.44000000)));
	}

	@Test
	public void testGetCloseRateOverTime_Date() throws Exception {
		mockMvc.perform(get("/getCloseRateOverTime/time?year=2016&month=04&day=22")).andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$.2016-04-22", Matchers.is(13.61000000)));
	}

	@Test
	public void testGetCloseRateOverTime_Exception() throws Exception {
		mockMvc.perform(get("/getCloseRateOverTime/time?year")).andExpect(status().is5xxServerError());
	}

	@Test
	public void testGetAverageCloseRateOverPeriod_Year() throws Exception {
		mockMvc.perform(get("/getAverageCloseRateOverPeriod/period?year=2016")).andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$.2016", Matchers.is(12.6310317460)));
	}

	@Test
	public void testGetAverageCloseRateOverPeriod_YearMonth() throws Exception {
		mockMvc.perform(get("/getAverageCloseRateOverPeriod/period?year=2016&month=4")).andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$.2016-4", Matchers.is(13.2071428571)));
	}

	@Test
	public void testGetAverageCloseRateOverPeriod_Date() throws Exception {
		mockMvc.perform(get("/getAverageCloseRateOverPeriod/period?year=2016&month=04&day=22"))
				.andExpect(status().isOk()).andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$.2016-04-22", Matchers.is(13.61000000)));
	}

	@Test
	public void testGetAverageCloseRateOverPeriod_Exception() throws Exception {
		mockMvc.perform(get("/getAverageCloseRateOverPeriod/period?year")).andExpect(status().is5xxServerError());
	}

	@Test
	public void testDefaultPath() throws Exception {
		mockMvc.perform(get("/badRequest")).andExpect(status().isBadRequest());
	}
}
