package io.appsforfree.oauth2_jersey.domain.exception;

import io.appsforfree.oauth2_jersey.domain.ErrorType;

public class InvalidClientException extends ErrorResponseException 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3923718179704604732L;

	public InvalidClientException(String errorMessage)
	{
		super.errorMessage = errorMessage;
	}
	
	@Override
	public int getStatus() { return 400; }
	
	@Override
	public ErrorType getErrorType() { return ErrorType.INVALIDCLIENT; }
}