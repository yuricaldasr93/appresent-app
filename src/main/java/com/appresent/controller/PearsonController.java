package com.appresent.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.appresent.domain.dto.PearsonDTO;
import com.appresent.domain.model.Pearson;
import com.appresent.service.PearsonService;

@RestController
@RequestMapping("/pearson")
public class PearsonController {

	@Autowired
	private PearsonService service;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("{id}")
	public ResponseEntity<PearsonDTO> get(@PathVariable Long id){
		PearsonDTO pearsonDTO = service.get(id)
			.map(pearson -> modelMapper.map(pearson, PearsonDTO.class))
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		return ResponseEntity.ok(pearsonDTO);
	}
	
	@GetMapping
	public Page<PearsonDTO> find(PearsonDTO pearsonDTO, Pageable pageRequest){
		Pearson pearsonEntity = modelMapper.map(pearsonDTO, Pearson.class);
		List<PearsonDTO> result = service.find(pearsonEntity, pageRequest)
				.getContent().stream().map(entity -> modelMapper.map(entity, PearsonDTO.class))
			.collect(Collectors.toList());
		
		return new PageImpl<>(result, pageRequest, result.size());
	}
	
	public ResponseEntity<PearsonDTO> add(@RequestBody PearsonDTO pearsonDTO){
		return null;
	}
}
