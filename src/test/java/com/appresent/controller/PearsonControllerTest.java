package com.appresent.controller;

import static com.appresent.utils.constants.ApiConstants.PEARSON_PATH;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
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

import com.appresent.domain.constants.ErrorConstants;
import com.appresent.domain.dto.FunctionDTO;
import com.appresent.domain.dto.GroupDTO;
import com.appresent.domain.dto.PearsonDTO;
import com.appresent.domain.exception.InexistentResourceException;
import com.appresent.domain.model.Function;
import com.appresent.domain.model.Group;
import com.appresent.domain.model.Pearson;
import com.appresent.infra.bean.BeanConfig;
import com.appresent.service.PearsonService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@WebMvcTest(controllers = {PearsonController.class, BeanConfig.class})
public class PearsonControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PearsonService service;
	
	@Test
	@DisplayName("Should return the specific pearson by id")
	void getByIdTest() throws Exception {
		long id = 1l;
		String name = "Astrides Jefferson";
		Pearson entity = createPearsonEntity(id, name);
		BDDMockito.given(service.get(Mockito.anyLong())).willReturn(Optional.of(entity));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(PEARSON_PATH.concat("/"+id))
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("data").exists())
		.andExpect(jsonPath("$.data.id").value(id))
		.andExpect(jsonPath("$.data.name").value(name))
		.andExpect(jsonPath("$.data.group.title").value(entity.getGroup().getTitle()))
		.andExpect(jsonPath("$.data.functions[*].title", Matchers.containsInAnyOrder(entity.getFunctions().get(0).getTitle())));
	}
	
	@Test
	@DisplayName("Should return BAD_REQUEST when get an inexistent id")
	void getInexistentTest() throws Exception {
		BDDMockito
			.doThrow(new InexistentResourceException(ErrorConstants.ERROR_INEXISTENT_RESOURCE))
			.when(service).get(Mockito.anyLong());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
				.get(PEARSON_PATH.concat("/"+1l))
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.errors[*].userMessage", Matchers.containsInAnyOrder(ErrorConstants.ERROR_INEXISTENT_RESOURCE)));
	}
	
	@Test
	@DisplayName("Should return all people")
	void findTest() throws Exception {
		String name1 = "Fulano de Tal";
		String name2 = "José João da Silva";
		Pearson pearsonEntity = createPearsonEntity(1l, name1);
		Pearson pearsonEntity2 = createPearsonEntity(2l, name2);
		List<Pearson> people = Arrays.asList(pearsonEntity, pearsonEntity2);
		
		BDDMockito.given(service.find(Mockito.any(Pearson.class), Mockito.any(Pageable.class)))
			.willReturn(new PageImpl<>(people, PageRequest.of(0, 15), people.size()));
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
			.get(PEARSON_PATH.concat("?page=0&size=15"))
			.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("data").exists())
		.andExpect(jsonPath("$.data.content", Matchers.hasSize(2)))
		.andExpect(jsonPath("$.data.content[*].id",Matchers.containsInAnyOrder(1, 2)))
		.andExpect(jsonPath("$.data.content[*].name",Matchers.containsInAnyOrder(name1, name2)));
	}
	
	@Test
	@DisplayName("Should filter people by name and group title")
	void filterNameTest() throws Exception {
		Pearson pearsonEntity = createPearsonEntity();
		
		BDDMockito.given(service.find(Mockito.any(Pearson.class), Mockito.any(Pageable.class)))
		.willReturn(new PageImpl<>(Arrays.asList(pearsonEntity), PageRequest.of(0, 15), 1));
	
		String queryString = String.format("?name=%s&group.title=%s&page=0&size=15",
				pearsonEntity.getName(),
				pearsonEntity.getGroup().getTitle());
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
			.get(PEARSON_PATH.concat(queryString))
			.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("data").exists())
		.andExpect(jsonPath("$.data.content", Matchers.hasSize(1)))
		.andExpect(jsonPath("$.data.content[0].id").value(pearsonEntity.getId()))
		.andExpect(jsonPath("$.data.content[0].name").value(pearsonEntity.getName()));
	}
	
	@Test
	@DisplayName("Should create a pearson")
	void savePearsonTest() throws Exception{
		PearsonDTO dto = createPearsonDTO();
		dto.setId(null);
		ModelMapper mapper = new ModelMapper();
		Pearson pearsonEntity = mapper.map(dto, Pearson.class);
		pearsonEntity.setId(1l);
		
		BDDMockito.given(service.save(Mockito.any(Pearson.class)))
		.willReturn(pearsonEntity);
		
		String body = new ObjectMapper().writeValueAsString(dto);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
		.post(PEARSON_PATH)
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.content(body);
		
		mockMvc.perform(request)
		.andExpect(status().isCreated())
		.andExpect(jsonPath("data").exists())
		.andExpect(jsonPath("$.data.id").value(1l));
	}
	
	@Test
	@DisplayName("Should update a pearson")
	void updatePearsonTest() throws Exception{
		String newName = "Evesclaudo Barreira";
		PearsonDTO pearsonDTO = createPearsonDTO();
		Pearson entityToUpdate = new ModelMapper().map(pearsonDTO, Pearson.class);
		
		pearsonDTO.setName(newName);
		Pearson updatedPearson = new ModelMapper().map(pearsonDTO, Pearson.class);
		updatedPearson.setName(newName);
		
		BDDMockito.given(service.get(Mockito.anyLong()))
			.willReturn(Optional.of(entityToUpdate));
		BDDMockito.given(service.update(Mockito.any(Pearson.class)))
			.willReturn(updatedPearson);
		
		String body = new ObjectMapper().writeValueAsString(pearsonDTO);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
			.put(PEARSON_PATH.concat("/"+entityToUpdate.getId()))
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.content(body);
		
		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("data").exists())
		.andExpect(jsonPath("$.data.id").value(updatedPearson.getId()))
		.andExpect(jsonPath("$.data.name").value(updatedPearson.getName()));
	}
	
	@Test
	@DisplayName("Should return BAD_REQUEST when try update an inexistent pearson")
	void updateInexistentPearsonTest() {
		
	}
	
	@Test
	@DisplayName("Should delete a pearson")
	void deletePearsonTest() throws Exception{
		
	}
	
	@Test
	@DisplayName("Should return BAD_REQUEST when try delete an inexistent pearson")
	void deleteInexistentPearsonTest() {
		
	}
	
	private PearsonDTO createPearsonDTO() {
		return PearsonDTO.builder().id(1l)
				.name("Celestina Ferreira")
				.group(GroupDTO.builder().id(1l).title("Senhoras").build())
				.functions(Arrays.asList(FunctionDTO.builder().id(1l).title("Grupo de Louvor").build()))
				.build();
				
	}

	private Pearson createPearsonEntity(Long id, String name) {
		return new Pearson(id, name, 
				Group.builder().id(1l).title("Varões").build(), 
				Arrays.asList(Function.builder().id(1l).title("Grupo de Louvor").build()));
	}
	
	private Pearson createPearsonEntity() {
		return Pearson.builder().id(1l)
				.name("José João Antônio")
				.group(Group.builder().id(1l).title("Varões").build())
				.functions(Arrays.asList(Function.builder().id(1l).title("Grupo de Louvor").build()))
				.build();
	}
}
