package io.appsforfree.oauth2_jersey.domain.exception;

import io.appsforfree.oauth2_jersey.domain.ErrorType;

public class UnauthorizedClientException extends ErrorResponseException 
{ 

	private static final long serialVersionUID = 943640324301396990L;

	public UnauthorizedClientException(String errorMessage)
	{
		super.errorMessage = errorMessage;
	}
	
	@Override
	public ErrorType getErrorType() { return ErrorType.UNAUTHORIZEDCLIENT; }
}
