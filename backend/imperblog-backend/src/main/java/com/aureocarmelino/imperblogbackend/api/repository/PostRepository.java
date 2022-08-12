package com.aureocarmelino.imperblogbackend.api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.aureocarmelino.imperblogbackend.model.entity.Post;
import com.aureocarmelino.imperblogbackend.model.entity.Author;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> 
{
	@Query("SELECT post FROM Post post ORDER BY post.pkPost DESC")
	List<Post> findAll();
	
	@Query("SELECT post FROM Post post WHERE post.author = :author ORDER BY post.pkPost DESC")
	List<Post> findAllAuthorPosts(Author author);
}