package io.appsforfree.oauth2_jersey.domain.exception;

import io.appsforfree.oauth2_jersey.domain.ErrorType;

public abstract class ErrorResponseException extends Exception 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -984042939714666398L;
	
	protected String errorMessage;
	
	public abstract ErrorType getErrorType();
	
	public String getErrorMessage() { return errorMessage; }
	public int getStatus() { return 400; }
}
