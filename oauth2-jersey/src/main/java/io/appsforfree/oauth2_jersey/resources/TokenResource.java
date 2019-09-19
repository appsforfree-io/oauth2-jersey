package io.appsforfree.oauth2_jersey.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/token")
public class TokenResource 
{
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayHtmlHello() 
	{
		return "Token endpoint";
	}
}
