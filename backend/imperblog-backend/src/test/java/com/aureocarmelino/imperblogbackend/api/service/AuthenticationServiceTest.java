package com.aureocarmelino.imperblogbackend.api.service;

import static com.aureocarmelino.imperblogbackend.util.constant.ConstantAuthentication.ERROR_EMAIL_UNAVAILABLE;
import static com.aureocarmelino.imperblogbackend.util.constant.ConstantAuthentication.ERROR_USER_UNAVAILABLE;
import static com.aureocarmelino.imperblogbackend.util.constant.ConstantAuthentication.ERROR_INVALID_GENDER;
import static org.mockito.ArgumentMatchers.any;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.aureocarmelino.imperblogbackend.api.repository.AuthorRepository;
import com.aureocarmelino.imperblogbackend.model.dto.SignUpDto.SignUpDto;
import com.aureocarmelino.imperblogbackend.model.entity.Author;
import com.aureocarmelino.imperblogbackend.model.entity.Gender;
import com.aureocarmelino.imperblogbackend.model.exception.BusinessException;

@SpringBootTest
class AuthenticationServiceTest 
{
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private AuthorRepository authorRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	@InjectMocks
	private AuthenticationService authenticationService;
	
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
	void whenSignupThenReturnAnAuthor() 
	{
		Mockito.when(authorRepository.findByEmail(any())).thenReturn( Optional.empty());
		Mockito.when(authorRepository.findByUsername(any())).thenReturn( Optional.empty());
		Mockito.when(passwordEncoder.encode(any())).thenReturn(PASSWORD);
		Mockito.when(modelMapper.map(any(), any())).thenReturn(author);
		Mockito.when(authorRepository.save(any())).thenReturn(author);
		
		Author newAuthor = authenticationService.signup(signUpDto);
		
		Assertions.assertNotNull(newAuthor);
		Assertions.assertEquals(PK_AUTHOR, newAuthor.getPkAuthor());
		Assertions.assertEquals(EMAIL, newAuthor.getEmail());
		Assertions.assertEquals(PASSWORD, newAuthor.getPassword());
		Assertions.assertEquals(USERNAME, newAuthor.getUsername());
		Assertions.assertEquals(GENDER, newAuthor.getGender());
		Assertions.assertEquals(author, newAuthor);
		Assertions.assertEquals(Author.class, newAuthor.getClass());
	}
	
	@Test
	void whenSignupThenReturnAnBusinessExceptionError_Invalid_Gender() 
	{
		try 
		{
			signUpDto.setGender("INVALID_GENDER_TEST");
			authenticationService.signup(signUpDto);
		} 
		catch (Exception e) 
		{
			Assertions.assertEquals(BusinessException.class, e.getClass());
			Assertions.assertEquals(ERROR_INVALID_GENDER, e.getMessage());
		}
	}
	
	@Test
	void whenSignupThenReturnAnBusinessExceptionError_Email_Unavailable() 
	{
		Mockito.when(authorRepository.findByEmail(any())).thenThrow(new BusinessException(ERROR_EMAIL_UNAVAILABLE));
		Mockito.when(authorRepository.findByUsername(any())).thenReturn(Optional.empty());
		Mockito.when(passwordEncoder.encode(any())).thenReturn(PASSWORD);
		Mockito.when(modelMapper.map(any(), any())).thenReturn(author);
		Mockito.when(authorRepository.save(any())).thenReturn(author);
		
		try 
		{
			authenticationService.signup(signUpDto);
		} 
		catch (Exception e) 
		{
			Assertions.assertEquals(BusinessException.class, e.getClass());
			Assertions.assertEquals(ERROR_EMAIL_UNAVAILABLE, e.getMessage());
		}
	}
	
	@Test
	void whenSignupThenReturnAnBusinessExceptionError_User_Unavailable() 
	{
		Mockito.when(authorRepository.findByEmail(any())).thenReturn(Optional.empty());
		Mockito.when(authorRepository.findByUsername(any())).thenThrow(new BusinessException(ERROR_USER_UNAVAILABLE));
		Mockito.when(passwordEncoder.encode(any())).thenReturn(PASSWORD);
		Mockito.when(modelMapper.map(any(), any())).thenReturn(author);
		Mockito.when(authorRepository.save(any())).thenReturn(author);
		
		try 
		{
			authenticationService.signup(signUpDto);
		} 
		catch (Exception e) 
		{
			Assertions.assertEquals(BusinessException.class, e.getClass());
			Assertions.assertEquals(ERROR_USER_UNAVAILABLE, e.getMessage());
		}
	}
}