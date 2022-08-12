package com.aureocarmelino.imperblogbackend.api.service;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import com.aureocarmelino.imperblogbackend.api.repository.PostRepository;
import com.aureocarmelino.imperblogbackend.model.dto.postDto.CreatePostDto;
import com.aureocarmelino.imperblogbackend.model.entity.Author;
import com.aureocarmelino.imperblogbackend.model.entity.Gender;
import com.aureocarmelino.imperblogbackend.model.entity.Post;
import com.aureocarmelino.imperblogbackend.model.exception.BusinessException;
import com.aureocarmelino.imperblogbackend.model.exception.ObjectNotFoundException;
import com.aureocarmelino.imperblogbackend.security.AuthorDetailsServiceImpl;
import static com.aureocarmelino.imperblogbackend.util.constant.ConstantPost.ERROR_POST_DOESNT_BELONG_LOGGED_USER;
import static com.aureocarmelino.imperblogbackend.util.constant.ConstantPost.ERROR_POST_NOT_FOUND;

@SpringBootTest
class PostServiceTest 
{
	@InjectMocks
	private PostService postService;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private PostRepository postRepository;
	
	@Mock
	private AuthorDetailsServiceImpl authorDetailsServiceImpl;
	
	private static final int INDEX = 0;
	
	private static final Long PK_POST = 1L;
	private static final String TITLE = "My new post";
	private static final String POST_CONTENT = "This is my new post content";
	
	private static final Long PK_AUTHOR = 1L;
	private static final String EMAIL = "aureo.roberto@hotmail.com";
	private static final String PASSWORD = "mypassword";
	private static final String USERNAME = "aureo.roberto";
	private static final Gender GENDER = Gender.M;

	private Post post;
	private Author author;
	private CreatePostDto createPostDto;
	private Optional<Post> optionalPost;
	
	@BeforeEach
	void init() throws Exception
	{
		MockitoAnnotations.openMocks(this);
		startInstances();
	}
	
	public void startInstances()
	{
		
		author = Author.builder()
					.pkAuthor(PK_AUTHOR)
					.email(EMAIL)
					.username(USERNAME)
					.password(PASSWORD)
					.gender(GENDER)
					.build();
		
		post = Post.builder()
				.pkPost(PK_POST)
				.title(TITLE)
				.postContent(POST_CONTENT)
				.author(author)
				.build();
		
		
		createPostDto = CreatePostDto.builder()
						.pkPost(PK_POST)
						.title(TITLE)
						.postContent(POST_CONTENT)
						.build();
		
		optionalPost = Optional.of(Post.builder()
				.pkPost(PK_POST)
				.title(TITLE)
				.postContent(POST_CONTENT)
				.author(author)
				.build());
	}
	

	@Test
	void whenCreateThenReturnAnPost() 
	{
		
		Mockito.when(postRepository.save(any())).thenReturn(post);
		Mockito.when(modelMapper.map(any(), any())).thenReturn(post);
		Mockito.when(authorDetailsServiceImpl.logged()).thenReturn(author);
		
		Post newPost = postService.create(createPostDto);
		
		Assertions.assertNotNull(newPost);
		Assertions.assertEquals(PK_POST, newPost.getPkPost());
		Assertions.assertEquals(TITLE, newPost.getTitle());
		Assertions.assertEquals(POST_CONTENT, newPost.getPostContent());
		Assertions.assertEquals(author, newPost.getAuthor());
		Assertions.assertEquals(Author.class, newPost.getAuthor().getClass());
		Assertions.assertEquals(Post.class, newPost.getClass());
	}
	
	@Test
	void whenFindAllThenReturnAnListOfPosts() 
	{
		Mockito.when(postRepository.findAll()).thenReturn(List.of(post));
		
		List<Post> posts = postService.findAll();
		
		Assertions.assertNotNull(posts);
		Assertions.assertEquals(PK_POST, posts.get(INDEX).getPkPost());
		Assertions.assertEquals(TITLE, posts.get(INDEX).getTitle());
		Assertions.assertEquals(POST_CONTENT, posts.get(INDEX).getPostContent());
		Assertions.assertEquals(author, posts.get(INDEX).getAuthor());
		Assertions.assertEquals(Author.class,  posts.get(INDEX).getAuthor().getClass());
		Assertions.assertEquals(Post.class,  posts.get(INDEX).getClass());
	}
	
	@Test
	void whenFindAllAuthorPostsThenReturnAnListOfAuthorPosts() 
	{
		Mockito.when(postRepository.findAllAuthorPosts(any())).thenReturn(List.of(post));
		
		List<Post> posts = postService.findAllAuthorPosts();
		
		Assertions.assertNotNull(posts);
		Assertions.assertEquals(PK_POST, posts.get(INDEX).getPkPost());
		Assertions.assertEquals(TITLE, posts.get(INDEX).getTitle());
		Assertions.assertEquals(POST_CONTENT, posts.get(INDEX).getPostContent());
		Assertions.assertEquals(author, posts.get(INDEX).getAuthor());
		Assertions.assertEquals(Author.class,  posts.get(INDEX).getAuthor().getClass());
		Assertions.assertEquals(Post.class,  posts.get(INDEX).getClass());
	}
	
	@Test
	void whenFindByIdThenReturnAnPost() 
	{
		Mockito.when(postRepository.findById(any())).thenReturn(optionalPost);
		
		Post posts = postService.findById(PK_POST);
		
		Assertions.assertNotNull(posts);
		Assertions.assertEquals(PK_POST, posts.getPkPost());
		Assertions.assertEquals(TITLE, posts.getTitle());
		Assertions.assertEquals(POST_CONTENT, posts.getPostContent());
		Assertions.assertEquals(author, posts.getAuthor());
		Assertions.assertEquals(Author.class, posts.getAuthor().getClass());
		Assertions.assertEquals(Post.class, posts.getClass());
	}
	
	
	@Test
	void whenFindByIdThenReturnAnObjectNotFoundException() 
	{
		Mockito.when(postRepository.findById(any())).thenThrow(new ObjectNotFoundException(ERROR_POST_NOT_FOUND));
		
		try 
		{
			Post posts = postService.findById(PK_POST);
			Assertions.assertNotNull(posts);
			Assertions.assertEquals(PK_POST, posts.getPkPost());
			Assertions.assertEquals(TITLE, posts.getTitle());
			Assertions.assertEquals(POST_CONTENT, posts.getPostContent());
			Assertions.assertEquals(author, posts.getAuthor());
			Assertions.assertEquals(Author.class, posts.getAuthor().getClass());
			Assertions.assertEquals(Post.class, posts.getClass());
		} 
		catch (Exception e) 
		{
			Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
			Assertions.assertEquals(ERROR_POST_NOT_FOUND, e.getMessage());
		}
	}
	
	@Test
	void whenEditThenReturnAnEditedPost() 
	{
		Mockito.when(postRepository.findById(any())).thenReturn(optionalPost);
		Mockito.when(postRepository.save(any())).thenReturn(post);
		Mockito.when(authorDetailsServiceImpl.logged()).thenReturn(author);
		
		Post editedPost = postService.edit(createPostDto);
		
		Assertions.assertNotNull(editedPost);
		Assertions.assertEquals(PK_POST, editedPost.getPkPost());
		Assertions.assertEquals(TITLE, editedPost.getTitle());
		Assertions.assertEquals(POST_CONTENT, editedPost.getPostContent());
		Assertions.assertEquals(author, editedPost.getAuthor());
		Assertions.assertEquals(Author.class, editedPost.getAuthor().getClass());
		Assertions.assertEquals(Post.class, editedPost.getClass());
	}
	
	@Test
	void whenEditThenReturnAnObjectNotFoundException() 
	{
		Mockito.when(postRepository.findById(any())).thenReturn(optionalPost);
		
		try 
		{
			postService.findById(PK_POST);
		} 
		catch (Exception e) 
		{
			Assertions.assertEquals(ObjectNotFoundException.class, e.getClass());
			Assertions.assertEquals(ERROR_POST_NOT_FOUND, e.getMessage());
		}
	}
	
	@Test
	void whenDeleteByIdOccursSuccessfully()
	{
		Mockito.when(authorDetailsServiceImpl.logged()).thenReturn(author);
		Mockito.when(postRepository.findById(any())).thenReturn(optionalPost);
		
		doNothing().when(postRepository).deleteById(anyLong());
		
		postService.deleteById(PK_POST);
		
		verify(postRepository, times(1)).deleteById(anyLong());
	}
	
	@Test
	void whenDeleteByIdThenReturnAnBusinessException() 
	{
		Mockito.when(postRepository.findById(anyLong())).thenThrow(new BusinessException(ERROR_POST_DOESNT_BELONG_LOGGED_USER));

		try 
		{
			postService.deleteById(PK_POST);
		} 
		catch (Exception ex) 
		{
			Assertions.assertEquals(BusinessException.class, ex.getClass());
			Assertions.assertEquals(ERROR_POST_DOESNT_BELONG_LOGGED_USER, ex.getMessage());
		}
	}
}