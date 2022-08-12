package com.aureocarmelino.imperblogbackend.api.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.aureocarmelino.imperblogbackend.api.service.ChartService;
import com.aureocarmelino.imperblogbackend.model.dto.chartDto.ChartDto;
import com.aureocarmelino.imperblogbackend.model.entity.Gender;


@SpringBootTest
class ChartControllerTest 
{
	@Mock
	private ChartService chartService;
	
	@InjectMocks
	private ChartController chartController;
	
	
	private static final Long TOTAL_MALE = 6L;
	private static final String MALE = Gender.M.getDescription();
	
	private static final Long TOTAL_FEMALE = 7L;
	private static final String FEMALE = Gender.F.getDescription();
	
	private ChartDto chartDtoMale;
	private ChartDto chartDtoFemale;
	
	
	@BeforeEach
	void init() throws Exception
	{
		MockitoAnnotations.openMocks(this);
		startInstances();
	}
	
	public void startInstances()
	{
		
		chartDtoMale = ChartDto.builder()
					.gender(MALE)
					.total(TOTAL_MALE) 
					.build();
		
		chartDtoFemale = ChartDto.builder()
				.gender(FEMALE)
				.total(TOTAL_FEMALE) 
				.build();
	}
	
	
	

	@Test
	void whenFindTotalAuthorsByGenderThenReturnAnListOfChartDto() 
	{
		Mockito.when(chartService.findTotalAuthorsByGender()).thenReturn(List.of(chartDtoMale,chartDtoFemale));
		
		ResponseEntity<List<ChartDto>> listChartDto = chartController.findTotalAuthorsByGender();
		
		 assertNotNull(listChartDto);
		 assertEquals(2, listChartDto.getBody().size());
		 assertEquals(HttpStatus.OK, listChartDto.getStatusCode());
		 assertEquals(ResponseEntity.class, listChartDto.getClass());
	}
}