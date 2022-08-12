package com.aureocarmelino.imperblogbackend.api.service;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import com.aureocarmelino.imperblogbackend.api.repository.AuthorRepository;
import com.aureocarmelino.imperblogbackend.model.dto.chartDto.ChartDto;
import com.aureocarmelino.imperblogbackend.model.entity.Gender;

@SpringBootTest
class ChartServiceTest 
{
	
	@Mock
	private AuthorRepository authorRepository;
	
	@InjectMocks
	private ChartService chartService;
	
	private static final Long TOTAL_MALE = 6L;
	private static final String MALE = Gender.M.getDescription();
	
	private static final Long TOTAL_FEMALE = 7L;
	private static final String FEMALE = Gender.F.getDescription();
	
	private ChartDto chartDtoMale;
	
	private ChartDto chartDtoFemale;
	
	private List<Object[]> objectsChartData;
		
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
		
		objectsChartData = Arrays.<Object[]>asList(
				new Object[]{chartDtoMale.getGender(), chartDtoMale.getTotal()},
				new Object[]{chartDtoFemale.getGender(), chartDtoFemale.getTotal()});
	}
	

	@Test
	void whenFindTotalAuthorsByGenderThenReturnChartData() 
	{
		Mockito.when(authorRepository.findTotalAuthorsByGender()).thenReturn(objectsChartData);
		
		List<ChartDto> listChartDto = chartService.findTotalAuthorsByGender();
		
		Assertions.assertNotNull(listChartDto);
		Assertions.assertEquals(MALE, listChartDto.get(0).getGender());
		Assertions.assertEquals(TOTAL_MALE, listChartDto.get(0).getTotal());
		Assertions.assertEquals(FEMALE, listChartDto.get(1).getGender());
		Assertions.assertEquals(TOTAL_FEMALE, listChartDto.get(1).getTotal());
	}

}