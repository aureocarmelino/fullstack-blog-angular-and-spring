package com.aureocarmelino.imperblogbackend.api.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.aureocarmelino.imperblogbackend.api.repository.AuthorRepository;
import com.aureocarmelino.imperblogbackend.model.dto.chartDto.ChartDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ChartService 
{
	private AuthorRepository authorRepository;
	
	public List<ChartDto> findTotalAuthorsByGender()
	{
		List<Object[]> list = this.authorRepository.findTotalAuthorsByGender();
		
		List<ChartDto> listChartDto = new ArrayList<>();
		
		for(Object[] elements: list)
		{	
			String gender = String.valueOf(elements[0]);
			Long total = Long.valueOf(String.valueOf(elements[1]));
			
			listChartDto.add(new ChartDto(gender,total,selectColorByGender(gender)));
		}   
		return listChartDto;
	}
	
	public String selectColorByGender(String gender)
	{
		// green: #1abc9c
		// rose:  #ffcbdb
		
		return gender.equals("M") ? "#1abc9c" : "#ffcbdb";
	}
}