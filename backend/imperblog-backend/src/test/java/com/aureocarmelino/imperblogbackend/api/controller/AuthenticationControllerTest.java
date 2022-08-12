package com.aureocarmelino.imperblogbackend.api.controller;


import static org.mockito.ArgumentMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.aureocarmelino.imperblogbackend.api.service.AuthenticationService;
import com.aureocarmelino.imperblogbackend.model.dto.SignUpDto.SignUpDto;
import com.aureocarmelino.imperblogbackend.model.entity.Author;
import com.aureocarmelino.imperblogbackend.model.entity.Gender;



@SpringBootTest
class AuthenticationControllerTest 
{
	@Mock
	private AuthenticationService authenticationService;
	
	@InjectMocks
	private AuthenticationController authenticationController;
	
	private static final Long PK_AUTHOR = 1L;
	private static final String EMAIL = "aureo.roberto@hotmail.com";
	private static final String PASSWORD = "mypassword";
	private static final String USERNAME = "aureo.roberto";
	private static final Gender GENDER = Gender.M;
	
	private SignUpDto signUpDto;
	private Author author;
	
	@BeforeEach
	void init() throws Exception
	{
		MockitoAnnotations.openMocks(this);
		startInstances();
	}
	
	public void startInstances()
	{
		
		author = Author.builder()
					.pkAuthor(PK_AUTHOR)
					.email(EMAIL)
					.username(USERNAME)
					.password(PASSWORD)
					.gender(GENDER)
					.build();
		
		signUpDto = SignUpDto.builder()
						.email(EMAIL)
						.password(PASSWORD)
						.username(USERNAME)
						.gender(GENDER.getDescription())
						.build();
	}
	
	@Test
	void whenSignupThenReturnCreated() 
	{
		 Mockito.when(authenticationService.signup(any())).thenReturn(author);
		 
		 ResponseEntity<Object> response = authenticationController.signup(signUpDto);
		 
		 assertEquals(HttpStatus.CREATED, response.getStatusCode());
		 assertNotNull(response.getHeaders().get("Location"));
		 assertEquals(ResponseEntity.class, response.getClass());
	}

}