package com.aureocarmelino.imperblogbackend.api.repository;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.aureocarmelino.imperblogbackend.model.entity.Author;
import com.aureocarmelino.imperblogbackend.model.entity.Gender;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class AuthorRepositoryTest 
{
	@Autowired
	private AuthorRepository authorRepository;
	
	private static final String EMAIL = "michael@hotmail.com";
	private static final String PASSWORD = "michaelpassword";
	private static final String USERNAME = "michael";
	private static final String USERNAME_UPDATE = "michael.update";
	private static final Gender GENDER = Gender.M;
	
	
	private static final String EMAIL2 = "michele@hotmail.com";
	private static final String PASSWORD2 = "michelepassword";
	private static final String USERNAME2 = "michele";
	private static final Gender GENDER2 = Gender.F;
	
	private Author authorMichael;
	private Author authorMichele;
	
	@BeforeEach
	void init() throws Exception
	{
		MockitoAnnotations.openMocks(this);
		startInstances();
	}
	
	public void startInstances()
	{
		
		authorMichael = Author.builder()
				.email(EMAIL)
				.username(USERNAME)
				.password(PASSWORD)
				.gender(GENDER)
				.build();
		
		authorMichele = Author.builder()
				.email(EMAIL2)
				.username(USERNAME2)
				.password(PASSWORD2)
				.gender(GENDER2)
				.build();
	}
	

	@Test
	void whenCreateThenPersistData() 
	{
		authorRepository.save(authorMichael); 
		
		assertNotNull(authorMichael.getPkAuthor());
		
		assertEquals(EMAIL, authorMichael.getEmail());
		assertEquals(PASSWORD, authorMichael.getPassword());
		assertEquals(USERNAME, authorMichael.getUsername());
		assertEquals(GENDER, authorMichael.getGender());
	}
	
	 @Test
	 public void whenDeleteThenRemoveData() 
	 {
        authorRepository.save(authorMichael);
        
        authorRepository.delete(authorMichael);
        
        assertThat(authorRepository.findById(authorMichael.getPkAuthor())).isEmpty();
	 }
	 
	 @Test
	 public void whenUpdateThenChangeAndPersistData() 
	 {
		authorRepository.save(authorMichael);
		
		authorMichael = authorRepository.findById(authorMichael.getPkAuthor()).orElse(null);
		authorMichael.setUsername(USERNAME_UPDATE);
	 	
	 	authorRepository.save(authorMichael);
	 	
	 	Author checkEditedAuthor = authorRepository.findById(authorMichael.getPkAuthor()).orElse(null);
	 	
	 	assertThat(checkEditedAuthor).isNotNull();
        assertThat(checkEditedAuthor.getUsername()).isEqualTo(USERNAME_UPDATE);
        assertThat(checkEditedAuthor.getEmail()).isEqualTo(EMAIL);
	 }
	 
	 @Test
	 public void whenFindAllThenReturnAnList() 
	 {
        this.authorRepository.save(authorMichael);
        this.authorRepository.save(authorMichele);

        List<Author> authorList = authorRepository.findAll();

        assertThat(authorList.size()).isEqualTo(2);
	 }
	 
	 
	 @Test
	 public void whenFindByEmailThenReturnData()
	 {
		authorRepository.save(authorMichael);
			
		Optional<Author> authorOptional = authorRepository.findByEmail(authorMichael.getEmail());
	 	
	 	assertThat(authorOptional).isNotNull();
	 	assertThat(authorOptional.get()).isNotNull();
        assertThat(authorOptional.get().getUsername()).isEqualTo(USERNAME);
        assertThat(authorOptional.get().getEmail()).isEqualTo(EMAIL);
	 }
	 
	 @Test
	 public void whenFindByEmailThenReturnException() 
	 {
		 authorMichael.setEmail("invalidemail@@@.com");
		 
		 Exception exception = assertThrows(ConstraintViolationException.class, ()-> authorRepository.save(authorMichael));
	     
	     assertEquals(ConstraintViolationException.class, exception.getClass());
	 }
	 
	 @Test
	 public void whenFindByUsernameThenReturnData()
	 {
		authorRepository.save(authorMichael);
			
		Optional<Author> authorOptional = authorRepository.findByUsername(authorMichael.getUsername());
	 	
	 	assertThat(authorOptional).isNotNull();
	 	assertThat(authorOptional.get()).isNotNull();
        assertThat(authorOptional.get().getUsername()).isEqualTo(USERNAME);
        assertThat(authorOptional.get().getEmail()).isEqualTo(EMAIL);
	 }
	 
	 @Test
	 public void whenFindByUsernameThenReturnException() 
	 {
		 Author author = Author.builder()
					.email(EMAIL)
					.password(PASSWORD)
					.gender(GENDER)
					.build();
		 
		 Exception exception = assertThrows(ConstraintViolationException.class, ()-> authorRepository.save(author));
	     
	     assertEquals(ConstraintViolationException.class, exception.getClass());
	 }
	 
	 @Test
	 public void whenFindTotalAuthorsByGenderThenReturnAnList()
	 {
		 authorRepository.save(authorMichael);
		 authorRepository.save(authorMichele);

	     List<Object[]> authorList = authorRepository.findTotalAuthorsByGender();

	     assertThat(authorList.size()).isEqualTo(2);
	 }

}