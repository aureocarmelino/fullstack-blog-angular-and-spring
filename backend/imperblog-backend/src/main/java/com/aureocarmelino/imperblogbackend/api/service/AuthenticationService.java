package com.aureocarmelino.imperblogbackend.api.service;

import static com.aureocarmelino.imperblogbackend.util.constant.ConstantAuthentication.ERROR_EMAIL_UNAVAILABLE;
import static com.aureocarmelino.imperblogbackend.util.constant.ConstantAuthentication.ERROR_INVALID_GENDER;
import static com.aureocarmelino.imperblogbackend.util.constant.ConstantAuthentication.ERROR_USER_UNAVAILABLE;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aureocarmelino.imperblogbackend.api.repository.AuthorRepository;
import com.aureocarmelino.imperblogbackend.model.dto.SignUpDto.SignUpDto;
import com.aureocarmelino.imperblogbackend.model.entity.Author;
import com.aureocarmelino.imperblogbackend.model.entity.Gender;
import com.aureocarmelino.imperblogbackend.model.exception.BusinessException;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class AuthenticationService 
{
	private ModelMapper modelMapper;
	
	private AuthorRepository authorRepository;
	
	private PasswordEncoder passwordEncoder;

	
	@Transactional
	public Author signup(SignUpDto signUpDto) throws BusinessException
	{
		if(signUpDto.getGender().equals(Gender.F.getDescription()) 
				|| signUpDto.getGender().equals(Gender.M.getDescription()))
		{
			Optional<Author> checkEmail = this.authorRepository.findByEmail(signUpDto.getEmail());
			
			Optional<Author> checkUsername = this.authorRepository.findByUsername(signUpDto.getUsername());
			
			if(checkEmail.isPresent()) 
				throw new BusinessException(ERROR_EMAIL_UNAVAILABLE);
			
			if(checkUsername.isPresent()) 
				throw new BusinessException(ERROR_USER_UNAVAILABLE);
			
			Author newAuthor = this.modelMapper.map(signUpDto, Author.class);
			
			newAuthor.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
			
			return this.authorRepository.save(newAuthor);
		}
		else
		{
			throw new BusinessException(ERROR_INVALID_GENDER);
		}
		
	}
}