package com.aureocarmelino.imperblogbackend.api.controller;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.aureocarmelino.imperblogbackend.api.service.PostService;
import com.aureocarmelino.imperblogbackend.model.dto.postDto.CreatePostDto;
import com.aureocarmelino.imperblogbackend.model.entity.Author;
import com.aureocarmelino.imperblogbackend.model.entity.Gender;
import com.aureocarmelino.imperblogbackend.model.entity.Post;


@SpringBootTest
class PostControllerTest 
{
	@Mock
	private PostService postService;
	
	@InjectMocks
	private PostController postController;
	
	private static final int INDEX = 0;
	
	private static final Long PK_POST = 1L;
	private static final String TITLE = "My new post";
	private static final String EDITED_TITLE = "EDITED TITLE";
	
	private static final String POST_CONTENT = "This is my new post content";
	
	private static final Long PK_AUTHOR = 1L;
	private static final String EMAIL = "aureo.roberto@hotmail.com";
	private static final String PASSWORD = "mypassword";
	private static final String USERNAME = "aureo.roberto";
	private static final Gender GENDER = Gender.M;

	private Post post, editedPost;
	private Author author;
	private CreatePostDto createPostDto;
	
	
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
		
		editedPost = Post.builder()
				.pkPost(PK_POST)
				.title(EDITED_TITLE)
				.postContent(POST_CONTENT)
				.author(author)
				.build();
	}

	
	@Test
	void whenCreatePostThenReturnAnPost() 
	{
		when(postService.create(any())).thenReturn(post);
		
		ResponseEntity<Post> newPost = postController.createPost(createPostDto);
		
		assertNotNull(newPost);
		assertEquals(HttpStatus.CREATED, newPost.getStatusCode());
		assertNotNull(newPost.getHeaders().get("Location"));
		assertEquals(ResponseEntity.class, newPost.getClass());
	}
	
	
	@Test
	void whenEditPostThenReturnAnEditedPost() 
	{
		when(postService.edit(any())).thenReturn(editedPost);
		
		ResponseEntity<Post> editedPost = postController.editPost(createPostDto);
		
		assertNotNull(editedPost);
		assertEquals(EDITED_TITLE, editedPost.getBody().getTitle());
		assertEquals(HttpStatus.OK, editedPost.getStatusCode());
		assertEquals(ResponseEntity.class, editedPost.getClass());
	}

	@Test
	void whenFindAllThenReturnListOfPost() 
	{
		when(postService.findAll()).thenReturn(List.of(post));
		
		ResponseEntity<List<Post>> posts = postController.findAll();
		
		Assertions.assertNotNull(posts);
		Assertions.assertEquals(HttpStatus.OK, posts.getStatusCode());
		Assertions.assertEquals(author, posts.getBody().get(INDEX).getAuthor());
		Assertions.assertEquals(Author.class,  posts.getBody().get(INDEX).getAuthor().getClass());
		Assertions.assertEquals(Post.class,  posts.getBody().get(INDEX).getClass());
		Assertions.assertEquals(PK_POST, posts.getBody().get(INDEX).getPkPost());
		Assertions.assertEquals(TITLE, posts.getBody().get(INDEX).getTitle());
		Assertions.assertEquals(POST_CONTENT, posts.getBody().get(INDEX).getPostContent());
	}

	
	@Test
	void whenFindAllAuthorPostsThenReturnAnListOfAuthorPosts() 
	{
		Mockito.when(postService.findAllAuthorPosts()).thenReturn(List.of(post));
		
		ResponseEntity<List<Post>> posts = postController.findAllAuthorPosts();
		
		Assertions.assertNotNull(posts);
		Assertions.assertEquals(HttpStatus.OK, posts.getStatusCode());
		Assertions.assertEquals(author, posts.getBody().get(INDEX).getAuthor());
		Assertions.assertEquals(Author.class,  posts.getBody().get(INDEX).getAuthor().getClass());
		Assertions.assertEquals(Post.class,  posts.getBody().get(INDEX).getClass());
		Assertions.assertEquals(PK_POST, posts.getBody().get(INDEX).getPkPost());
		Assertions.assertEquals(TITLE, posts.getBody().get(INDEX).getTitle());
		Assertions.assertEquals(POST_CONTENT, posts.getBody().get(INDEX).getPostContent());
	}

	@Test
	void whenFindByIdThenReturnAnPost() 
	{
		Mockito.when(postService.findById(any())).thenReturn(post);
		
		ResponseEntity<Post> post = postController.findById(PK_POST);
		
		Assertions.assertNotNull(post);
		Assertions.assertEquals(HttpStatus.OK, post.getStatusCode());
		Assertions.assertEquals(PK_POST, post.getBody().getPkPost());
		Assertions.assertEquals(TITLE, post.getBody().getTitle());
		Assertions.assertEquals(POST_CONTENT, post.getBody().getPostContent());
		Assertions.assertEquals(author, post.getBody().getAuthor());
		Assertions.assertEquals(Author.class, post.getBody().getAuthor().getClass());
		Assertions.assertEquals(Post.class, post.getBody().getClass());
	}

	
	@Test
	void whenDeleteByIdOccursSuccessfully() 
	{
		doNothing().when(postService).deleteById(anyLong());
		
		ResponseEntity<Post> response = postController.deleteById(PK_POST);
		
		assertNotNull(response);
		assertEquals(ResponseEntity.class, response.getClass());
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		verify(postService, times(1)).deleteById(anyLong());
		
	}

}