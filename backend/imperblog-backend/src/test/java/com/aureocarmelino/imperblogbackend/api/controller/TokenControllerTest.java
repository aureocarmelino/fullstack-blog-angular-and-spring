package com.aureocarmelino.imperblogbackend.api.controller;

import static org.mockito.Mockito.mock;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TokenControllerTest 
{
	@InjectMocks
	private TokenController tokenController;
	
	private HttpServletRequest mockRequest;
	private HttpServletResponse mockResponse;
	
	@BeforeEach
	void init() throws Exception
	{
		MockitoAnnotations.openMocks(this);
		startInstances();
	}
	
	public void startInstances()
	{
		 mockRequest = mock(HttpServletRequest.class);
		 
		 mockResponse = mock(HttpServletResponse.class);
	}
	

	@Test
	void whenRevokeThenReturnStatusCode204() 
	{
		tokenController.revoke(mockRequest, mockResponse);
	}
}