package com.appresent.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

//@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = PearsonController.class)
public class PearsonControllerTest {

	private static String PEARSON_API = "/pearson";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Should return all people")
	public void findAllTest() {
		
	}
	
	@Test
	@DisplayName("Should filtler people by name")
	public void filterNameTest() {
		
	}
}
