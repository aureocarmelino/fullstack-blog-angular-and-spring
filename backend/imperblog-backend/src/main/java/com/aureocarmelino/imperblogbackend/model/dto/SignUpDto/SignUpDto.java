package com.aureocarmelino.imperblogbackend.model.dto.SignUpDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto 
{
	@NotBlank(message = "Enter password")
	private String password;
	
	@NotBlank(message = "Enter email")
    @Email(message = "Enter a valid email")
	private String email;
	
	@NotBlank(message = "Enter username")
	private String username;
	
	@NotBlank(message = "Enter gender")
	private String gender;
}