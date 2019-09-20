package io.appsforfree.oauth2_jersey.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import io.appsforfree.oauth2_jersey.domain.TokenResponse;
import io.appsforfree.oauth2_jersey.domain.request.TokenRequest;

@Path("/token")
public class TokenResource 
{
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response handleTokenRequest(MultivaluedMap<String, String> body) 
	{
		TokenResponse tokenResponse = new TokenResponse("123456", "Bearer");
		return Response.ok().entity(tokenResponse).build();
	}
}
