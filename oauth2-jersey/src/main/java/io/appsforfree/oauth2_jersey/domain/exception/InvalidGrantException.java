package io.appsforfree.oauth2_jersey.domain.exception;

import io.appsforfree.oauth2_jersey.domain.ErrorType;

public class InvalidGrantException extends ErrorResponseException 
{

	private static final long serialVersionUID = 2777243092122564527L;

	public InvalidGrantException(String errorMessage)
	{
		super.errorMessage = errorMessage;
	}
	
	@Override
	public ErrorType getErrorType() { return ErrorType.INVALIDGRANT; }
}
