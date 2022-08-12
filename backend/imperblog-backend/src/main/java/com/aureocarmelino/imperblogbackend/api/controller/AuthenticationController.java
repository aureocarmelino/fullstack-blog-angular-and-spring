package com.aureocarmelino.imperblogbackend.api.controller;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.aureocarmelino.imperblogbackend.api.service.AuthenticationService;
import com.aureocarmelino.imperblogbackend.model.dto.SignUpDto.SignUpDto;
import com.aureocarmelino.imperblogbackend.model.entity.Author;
import com.aureocarmelino.imperblogbackend.model.exception.BusinessException;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController 
{
	private AuthenticationService authenticationService;
	
	private static final String ID = "/{id}";
	
	@PostMapping("/signup")
	public ResponseEntity<Object> signup(@Validated @RequestBody SignUpDto signUpDto) throws BusinessException
	{
		Author author = this.authenticationService.signup(signUpDto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(author.getPkAuthor()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
}