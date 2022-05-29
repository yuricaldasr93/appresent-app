package com.appresent.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.appresent.domain.model.Pearson;

public interface PearsonService {

	Optional<Pearson> get(long id);

	Page<Pearson> find(Pearson pearson, Pageable pageRequest);
}
