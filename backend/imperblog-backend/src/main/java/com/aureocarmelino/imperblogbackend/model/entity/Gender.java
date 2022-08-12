package com.aureocarmelino.imperblogbackend.model.entity;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@NotBlank
public enum Gender 
{
	M(1,"M"),
	F(2,"F");
	
	private Integer pkGender;
	private String description;
}