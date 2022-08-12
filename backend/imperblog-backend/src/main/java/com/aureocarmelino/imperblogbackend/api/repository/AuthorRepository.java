package com.aureocarmelino.imperblogbackend.api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.aureocarmelino.imperblogbackend.model.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>
{
	Optional<Author> findByEmail(String email);
	
	Optional<Author> findByUsername(String username);
	
	@Query("SELECT author.gender, count(gender) FROM Author author group by author.gender")
	List<Object[]> findTotalAuthorsByGender();
}