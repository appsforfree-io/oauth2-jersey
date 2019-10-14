package io.appsforfree.oauth2_jersey.domain.exception;

import io.appsforfree.oauth2_jersey.domain.ErrorType;

public class InvalidRequestException extends ErrorResponseException 
{
	private static final long serialVersionUID = -3923718179704604731L;

	public InvalidRequestException(String errorMessage)
	{
		super.errorMessage = errorMessage;
	}
	
	@Override
	public ErrorType getErrorType() { return ErrorType.INVALIDREQUEST; }
}
