package com.aureocarmelino.imperblogbackend.model.dto.chartDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ChartDto 
{
	private String gender;
	private Long total;
	private String color;
}
