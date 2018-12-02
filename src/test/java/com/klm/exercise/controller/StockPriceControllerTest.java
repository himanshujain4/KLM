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
	public void testGetClosePriceForEntireTimeSpanOfFile() throws Exception {
		mockMvc.perform(get("/getClosePriceForEntireTimeSpanOfFile")).andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$.2000-05-31", Matchers.is(26.68725600)))
				.andExpect(jsonPath("$.2000-06-02", Matchers.is(26.58421700)));
		;
	}

	@Test
	public void testGetClosePriceBetweenDates() throws Exception {
		mockMvc.perform(get("/getClosePriceBetweenDates/{fromDate}/{toDate}", "01-06-2000", "06-06-2001"))
				.andExpect(status().isOk()).andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$.2001-06-05", Matchers.is(27.95807800)))
				.andExpect(jsonPath("$.2000-06-02", Matchers.is(26.58421700)));
		;
	}

	@Test
	public void testGetAverageClosePriceWithinYear() throws Exception {
		mockMvc.perform(get("/getAverageClosePriceWithinYear/{year}", "2001")).andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$.2001", Matchers.is(27.7176520000)));
		;
	}

	@Test
	public void testGetAverageClosePriceWithinMonthOfYear() throws Exception {
		mockMvc.perform(get("/getAverageClosePriceWithinMonthOfYear/{year}/{month}", "2001", "06"))
				.andExpect(status().isOk()).andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$.2001/06", Matchers.is(27.7176520000)));
		;
	}

	@Test
	public void testGetClosePriceForDate() throws Exception {
		mockMvc.perform(get("/getClosePriceForDate/{year}/{month}/{day}", "2001", "06", "05"))
				.andExpect(status().isOk()).andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$.2001/06/05", Matchers.is(27.95807800)));
		;
	}

	@Test
	public void testDefaultPath() throws Exception {
		mockMvc.perform(get("/badRequest")).andExpect(status().isBadRequest());
	}
}
