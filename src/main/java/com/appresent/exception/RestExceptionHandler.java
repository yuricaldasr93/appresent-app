package com.appresent.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.appresent.domain.dto.ErrorsDTO;
import com.appresent.domain.exception.InexistentResourceException;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(value = InexistentResourceException.class)
	public ResponseEntity<ErrorsDTO> handleInexistenteResourceException(InexistentResourceException irexc, WebRequest request){
		
		return null;
	}
}
