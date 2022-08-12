package com.aureocarmelino.imperblogbackend.api.exception;

import java.time.OffsetDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class Problem 
{
	private Integer status;
	private OffsetDateTime dateHours;
	private String title;
	private List<Field> fieldErrors;
	private String path;
}
