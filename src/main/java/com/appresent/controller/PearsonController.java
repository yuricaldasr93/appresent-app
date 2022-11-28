package com.appresent.controller;

import static com.appresent.utils.constants.ApiConstants.PEARSON_PATH;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.appresent.domain.constants.ErrorConstants;
import com.appresent.domain.dto.PearsonDTO;
import com.appresent.domain.dto.ResponseDTO;
import com.appresent.domain.exception.InexistentResourceException;
import com.appresent.domain.model.Function;
import com.appresent.domain.model.Group;
import com.appresent.domain.model.Pearson;
import com.appresent.service.PearsonService;

@RestController
@RequestMapping(PEARSON_PATH)
public class PearsonController {

	@Autowired
	private PearsonService service;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("{id}")
	public ResponseEntity<ResponseDTO<PearsonDTO>> get(@PathVariable Long id){
		PearsonDTO pearsonDTO = service.get(id)
			.map(pearson -> modelMapper.map(pearson, PearsonDTO.class))
			.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
		
		ResponseDTO<PearsonDTO> response = new ResponseDTO<>();
		response.setData(pearsonDTO);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping
	public ResponseEntity<ResponseDTO<Page<PearsonDTO>>> find(PearsonDTO pearsonDTO, Pageable pageRequest){
		Pearson pearsonEntity = modelMapper.map(pearsonDTO, Pearson.class);
		List<PearsonDTO> result = service.find(pearsonEntity, pageRequest)
				.getContent().stream().map(entity -> modelMapper.map(entity, PearsonDTO.class))
			.collect(Collectors.toList());
		
		ResponseDTO<Page<PearsonDTO>> response = new ResponseDTO<>();
		response.setData(new PageImpl<>(result, pageRequest, result.size()));
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<ResponseDTO<PearsonDTO>> create(@RequestBody @Valid PearsonDTO pearsonDTO){
		Pearson entity = modelMapper.map(pearsonDTO, Pearson.class);
		entity = service.save(entity);
		
		ResponseDTO<PearsonDTO> response = new ResponseDTO<>();
		response.setData(modelMapper.map(entity, PearsonDTO.class));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ResponseDTO<PearsonDTO>> update(@PathVariable Long id, @RequestBody @Valid PearsonDTO pearsonDTO){
		Pearson newEntity = service.get(pearsonDTO.getId()).map(pearson -> {
			pearson.setName(pearsonDTO.getName());
			pearson.setGroup(modelMapper.map(pearsonDTO.getGroup(), Group.class));
			List<Function> newFunctions = pearsonDTO.getFunctions()
					.stream()
					.map(func -> {return modelMapper.map(func, Function.class);})
					.collect(Collectors.toList());
			pearson.setFunctions(newFunctions);
			return service.update(pearson);			
		}).orElseThrow(() -> new InexistentResourceException(ErrorConstants.ERROR_INEXISTENT_RESOURCE));
		
		PearsonDTO newDTO = modelMapper.map(newEntity, PearsonDTO.class);
		ResponseDTO<PearsonDTO> response = new ResponseDTO<>();
		response.setData(newDTO);
		return ResponseEntity.ok(response);
	}
}
