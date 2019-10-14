package io.appsforfree.oauth2_jersey.domain.exception;

import io.appsforfree.oauth2_jersey.domain.ErrorType;

public class InvalidScopeException extends ErrorResponseException 
{

	private static final long serialVersionUID = -1937407075251682346L;
	
	public InvalidScopeException(String errorMessage)
	{
		super.errorMessage = errorMessage;
	}
	
	@Override
	public ErrorType getErrorType() {
		// TODO Auto-generated method stub
		return ErrorType.INVALIDSCOPE;
	}

}
