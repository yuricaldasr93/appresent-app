package com.appresent.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.appresent.domain.constants.Constants;
import com.appresent.domain.exception.InexistentResourceException;
import com.appresent.domain.model.Pearson;
import com.appresent.infra.bean.BeanConfig;
import com.appresent.service.PearsonService;

@ActiveProfiles("test")
@WebMvcTest(controllers = {PearsonController.class, BeanConfig.class})
public class PearsonControllerTest {

	private static String PEARSON_API = "/pearson";
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PearsonService service;
	
	@Test
	@DisplayName("Should return the specific pearson by id")
	public void getTest() throws Exception {
		long id = 1l;
		String name = "Astrides Jefferson";
		BDDMockito.given(service.get(Mockito.anyLong())).willReturn(Optional.of(new Pearson(id, name)));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(PEARSON_API.concat("/"+id))
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("id").value(id))
		.andExpect(jsonPath("name").value(name));
	}
	
	@Test
	@DisplayName("Should return BAD_REQUEST when get an inexistent id")
	public void getInexistentTest() throws Exception {
		BDDMockito.doThrow(new InexistentResourceException(Constants.INEXISTENT_RESOURCE)).when(service).get(Mockito.anyLong());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(PEARSON_API.concat("/"+1l))
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.erros[*]", Matchers.containsInAnyOrder(Constants.INEXISTENT_RESOURCE)));
	}
	
	@Test
	@DisplayName("Should return all people")
	public void findTest() throws Exception {
		String name1 = "Fulano de Tal";
		String name2 = "José João da Silva";
		Pearson pearsonEntity = createPerasonEntity(1l, name1);
		Pearson pearsonEntity2 = createPerasonEntity(2l, name2);
		List<Pearson> people = Arrays.asList(pearsonEntity, pearsonEntity2);
		
		BDDMockito.given(service.find(Mockito.any(Pearson.class), Mockito.any(Pageable.class)))
			.willReturn(new PageImpl<>(people, PageRequest.of(0, 5), people.size()));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
			.get(PEARSON_API.concat("?page=0&size=15"))
			.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("content", Matchers.hasSize(2)))
		.andExpect(jsonPath("$.content[*].id",Matchers.containsInAnyOrder(1, 2)))
		.andExpect(jsonPath("$.content[*].name",Matchers.containsInAnyOrder(name1, name2)));
	}
	
	@Test
	@DisplayName("Should return BAD_REQUEST ")
	public void findWithoutPageableTest() {
		
	}
	
	@Test
	@DisplayName("Should filter people by name")
	public void filterNameTest() {
		
	}
	
	private Pearson createPerasonEntity(Long id, String name) {
		return new Pearson(id, name);
	}
}
