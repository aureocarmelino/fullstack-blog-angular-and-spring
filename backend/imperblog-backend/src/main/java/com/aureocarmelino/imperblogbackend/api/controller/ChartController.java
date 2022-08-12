package com.aureocarmelino.imperblogbackend.api.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aureocarmelino.imperblogbackend.api.service.ChartService;
import com.aureocarmelino.imperblogbackend.model.dto.chartDto.ChartDto;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController 
@RequestMapping("/api/chart")
public class ChartController 
{
	private ChartService chartService;
 
	@GetMapping
	public ResponseEntity<List<ChartDto>> findTotalAuthorsByGender()
	{
		return ResponseEntity.status(HttpStatus.OK).body(this.chartService.findTotalAuthorsByGender());
	}
}