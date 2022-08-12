package com.aureocarmelino.imperblogbackend.api.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.aureocarmelino.imperblogbackend.model.entity.Author;
import com.aureocarmelino.imperblogbackend.model.entity.Gender;
import com.aureocarmelino.imperblogbackend.model.entity.Post;


@ExtendWith(SpringExtension.class)
@DataJpaTest
class PostRepositoryTest 
{
@Autowired
private PostRepository postRepository;

@Autowired
private AuthorRepository authorRepository;

private static final String TITLE = "My new post";
private static final String TITLE_UPDATE = "My edited post";
private static final String POST_CONTENT = "This is my new post content";

private static final String EMAIL = "michael@hotmail.com";
private static final String PASSWORD = "michaelpassword";
private static final String USERNAME = "michael";
private static final Gender GENDER = Gender.M;

private Author authorMichael;
private Post post;

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
	
	post = Post.builder()
			.title(TITLE)
			.postContent(POST_CONTENT)
			.author(authorMichael)
			.build();
}


@Test
void whenCreateThenPersistData() 
{
	authorRepository.save(authorMichael);
	postRepository.save(post); 
	
	assertNotNull(post.getPkPost());
	assertEquals(TITLE, post.getTitle());
	assertEquals(POST_CONTENT, post.getPostContent());
	assertEquals(authorMichael, post.getAuthor());
}

@Test
public void whenDeleteThenRemoveData() 
{
	authorRepository.save(authorMichael);
	postRepository.save(post); 
	
	postRepository.delete(post);
   
	assertThat(postRepository.findById(post.getPkPost())).isEmpty();
}

 @Test
 public void whenUpdateThenChangeAndPersistData() 
 {
	authorRepository.save(authorMichael);
	postRepository.save(post); 
	
	post = postRepository.findById(post.getPkPost()).orElse(null);
	post.setTitle(TITLE_UPDATE);
 	
	postRepository.save(post);
 	
 	Post checkEditedPost = postRepository.findById(post.getPkPost()).orElse(null);
 	
 	assertThat(checkEditedPost).isNotNull();
    assertThat(checkEditedPost.getTitle()).isEqualTo(TITLE_UPDATE);
    assertThat(checkEditedPost.getPostContent()).isEqualTo(POST_CONTENT);
 }
 
 @Test
 public void whenFindAllThenReturnAnList() 
 {
	authorRepository.save(authorMichael);
    this.postRepository.save(post);

    List<Post> postList = postRepository.findAll();

    assertThat(postList.size()).isEqualTo(1);
 }
 
 @Test
 public void whenFindAllAuthorPostsThenReturnAnList() 
 {
	authorRepository.save(authorMichael);
    this.postRepository.save(post);

    List<Post> postList = postRepository.findAllAuthorPosts(authorMichael);

    assertThat(postList.size()).isEqualTo(1);
 }
}
