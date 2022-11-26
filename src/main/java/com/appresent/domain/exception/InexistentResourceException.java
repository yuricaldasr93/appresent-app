package com.appresent.domain.exception;

public class InexistentResourceException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public InexistentResourceException(String friendlyMeString) {
		super(friendlyMeString);
	}
	
	public InexistentResourceException(String friendlyMessage, String technicalMessage) {
		super(friendlyMessage, technicalMessage);
	}
	
	public InexistentResourceException(String friendlyMessage, String technicalMessage, Throwable cause) {
		super(friendlyMessage, technicalMessage, cause);
	}
}
