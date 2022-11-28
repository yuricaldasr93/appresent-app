package com.appresent.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.appresent.domain.model.Group;
import com.appresent.infra.bean.BeanConfig;
import com.appresent.service.GroupService;
import com.appresent.utils.constants.ApiConstants;

@ActiveProfiles("test")
@WebMvcTest(controllers = {GroupController.class, BeanConfig.class})
public class GroupControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private GroupService groupService;
	
	@Test
	@DisplayName("Deve buscar o grupo pelo id")
	public void finadByIdSuccessTest() throws Exception {
		long id = 1l;
		String title = "Adolescentes";
		
		Group entity = Group.builder().id(id).title(title).build();
		BDDMockito.given(groupService.findById(Mockito.anyLong()))
		.willReturn(entity);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders
		.get(ApiConstants.GROUP_PATH+"/"+id)
		.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(request)
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").exists())
		.andExpect(jsonPath("$.data.id").value(1l))
		.andExpect(jsonPath("$.data.title").value(title));
	}
}
