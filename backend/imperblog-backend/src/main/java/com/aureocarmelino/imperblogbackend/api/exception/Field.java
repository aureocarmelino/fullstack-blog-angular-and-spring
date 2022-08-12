package com.aureocarmelino.imperblogbackend.api.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Field 
{
	private String name;
	private String error;
}
