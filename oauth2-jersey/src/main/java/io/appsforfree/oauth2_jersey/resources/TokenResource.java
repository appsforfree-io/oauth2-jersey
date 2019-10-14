package io.appsforfree.oauth2_jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import io.appsforfree.oauth2_jersey.business.TokenRequestHandler;
import io.appsforfree.oauth2_jersey.business.TokenRequestManager;
import io.appsforfree.oauth2_jersey.domain.ErrorResponse;
import io.appsforfree.oauth2_jersey.domain.TokenResponse;
import io.appsforfree.oauth2_jersey.domain.exception.ErrorResponseException;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequestFactory;

@Path("/token")
public class TokenResource 
{
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response handleTokenRequest(
			@HeaderParam("Authorization") String authorization, 
			MultivaluedMap<String, String> body) 
	{
		try
		{
			TokenRequest tokenRequest = TokenRequestFactory.createRequest(body, authorization);
			TokenResponse tokenResponse = TokenRequestHandler.handleTokenRequest(tokenRequest);
			if (tokenResponse == null)
				return Response.status(400).build();
			return Response.ok().entity(tokenResponse).build();
		}
		catch (ErrorResponseException e)
		{
			return Response
					.status(e.getStatus())
					.entity(new ErrorResponse(
							e.getErrorType(), 
							e.getMessage(), 
							null))
					.build();
		}
		catch (Exception e)
		{
			System.out.println("EXCEPTION THROWN: "+e.getMessage());
			return Response.status(500).build();
		}
	}
}
