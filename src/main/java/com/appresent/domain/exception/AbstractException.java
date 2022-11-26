package com.appresent.domain.exception;

import com.appresent.domain.dto.ErrorsDTO;

public abstract class AbstractException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	private String friendlyMessage;
	private String technicalMessage;
	
	protected AbstractException(String friendlyMessage) {
		super(friendlyMessage);
		this.friendlyMessage = friendlyMessage;
	}
	
	protected AbstractException(String friendlyMessage, String technicalMessage) {
		super(friendlyMessage);
		this.friendlyMessage = friendlyMessage;
		this.technicalMessage = technicalMessage;
	}

	protected AbstractException(String friendlyMessage, String technicalMessage, Throwable cause) {
		super(friendlyMessage, cause);
		this.friendlyMessage = friendlyMessage;
		this.technicalMessage = technicalMessage;
	}
	
	public ErrorsDTO toResponse() {
		return ErrorsDTO.builder().userMessage(friendlyMessage)
			.developerMessage(technicalMessage).build();
	}
}
