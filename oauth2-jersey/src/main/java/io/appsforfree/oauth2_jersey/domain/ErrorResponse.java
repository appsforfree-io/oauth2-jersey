package io.appsforfree.oauth2_jersey.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponse 
{
	public static final String INVALID_REQUEST = "invalid_request";
	public static final String INVALID_CLIENT = "invalid_client";
	public static final String INVALID_GRANT = "invalid_grant";
	public static final String UNAUTHORIZED_CLIENT = "unauthorized_client";
	public static final String UNSUPPORTED_GRANT_TYPE = "unsupported_grant_type";
	public static final String INVALID_SCOPE = "invalid_scope";
	
	@JsonProperty("error")
	private String error;
	@JsonProperty("error_description")
	private String errorDescription;
	@JsonProperty("error_uri")
	private String errorUri;
	
	public ErrorResponse() {}
	
	public ErrorResponse(
			ErrorType errorType, 
			String errorDescription, 
			String errorUri)
	{
		this.error = getError(errorType);
		this.errorDescription = errorDescription;
		this.errorUri = errorUri;
	}
	
	private String getError(ErrorType errorType)
	{
		switch (errorType)
		{
			case INVALIDREQUEST:
				return INVALID_REQUEST;
			case INVALIDCLIENT:
				return INVALID_CLIENT;
			case INVALIDGRANT:
				return INVALID_GRANT;
			case UNAUTHORIZEDCLIENT:
				return UNAUTHORIZED_CLIENT;
			case UNSUPPORTEDGRANTTYPE:
				return UNSUPPORTED_GRANT_TYPE;
			case INVALIDSCOPE:
				return INVALID_SCOPE;
			default:
				return null;
		}
	}
	
	public String getError() { return error;}
	public void setError(String error) { this.error = error; }
	
	public String getErrorDescription() { return errorDescription; }
	public void setErrorDescription(String errorDescription) { this.errorDescription = errorDescription; }
	
	public String getErrorUri() { return errorUri; }
	public void setErrorUri(String errorUri) { this.errorUri = errorUri; }
}
