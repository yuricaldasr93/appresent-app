package com.appresent.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appresent.domain.dto.GroupDTO;
import com.appresent.domain.dto.ResponseDTO;
import com.appresent.utils.constants.ApiConstants;

@RestController
@RequestMapping(ApiConstants.GROUP_PATH)
public class GroupController {
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO<GroupDTO>> getById(@PathVariable Long id) {
		return null;
	}
}
