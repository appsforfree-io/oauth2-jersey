package io.appsforfree.oauth2_jersey.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import io.appsforfree.oauth2_jersey.domain.TokenResponse;

@Path("/token")
public class TokenResource 
{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response sayHtmlHello() 
	{
		TokenResponse tokenResponse = new TokenResponse("123456", "Bearer");
		return Response.ok().entity(tokenResponse).build();
	}
}
