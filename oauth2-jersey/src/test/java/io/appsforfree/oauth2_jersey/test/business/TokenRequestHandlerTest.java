package io.appsforfree.oauth2_jersey.test.business;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.appsforfree.oauth2_jersey.business.TokenRequestHandler;
import io.appsforfree.oauth2_jersey.domain.exception.UnsupportedGrantTypeException;

public class TokenRequestHandlerTest 
{
	@Test
	public void testHandleRequest_unsupportedGrantType_throwUnsupportedGrantTypeException()
	{
		assertThrows(UnsupportedGrantTypeException.class, () -> {
			TokenRequestHandler.handleTokenRequest(null);
		});
	}
}
