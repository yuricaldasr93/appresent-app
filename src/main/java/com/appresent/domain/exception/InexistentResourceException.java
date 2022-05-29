package com.appresent.domain.exception;

public class InexistentResourceException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public InexistentResourceException(String message) {
		super(message);
	}
}
