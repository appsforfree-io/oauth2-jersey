package io.appsforfree.oauth2_jersey.domain.exception;

import io.appsforfree.oauth2_jersey.domain.ErrorType;

public class UnsupportedGrantTypeException extends ErrorResponseException 
{

	private static final long serialVersionUID = -4530971722965688182L;

	public UnsupportedGrantTypeException(String errorMessage)
	{
		super.errorMessage = errorMessage;
	}
	
	@Override
	public ErrorType getErrorType() { return ErrorType.UNSUPPORTEDGRANTTYPE; }
}
