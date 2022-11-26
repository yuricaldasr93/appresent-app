package com.appresent.service.impl;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.appresent.domain.model.Pearson;
import com.appresent.service.PearsonService;

@Service
public class PearsonServiceImpl implements PearsonService {

	@Override
	public Optional<Pearson> get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Pearson> find(Pearson pearson, Pageable pageRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pearson save(Pearson pearson) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pearson update(Pearson pearson) {
		// TODO Auto-generated method stub
		return null;
	}

}
