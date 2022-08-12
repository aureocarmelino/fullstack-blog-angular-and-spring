package com.aureocarmelino.imperblogbackend.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.aureocarmelino.imperblogbackend.api.service.PostService;
import com.aureocarmelino.imperblogbackend.model.dto.postDto.CreatePostDto;
import com.aureocarmelino.imperblogbackend.model.entity.Post;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController 
@RequestMapping("/api/posts")
public class PostController 
{ 
	private PostService postService;
	
	private static final String ID = "/{id}";
	
	@PostMapping
	public ResponseEntity<Post> createPost(@Valid @RequestBody CreatePostDto createPostDto)
	{
		Post post = this.postService.create(createPostDto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(post.getPkPost()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping
	public ResponseEntity<Post> editPost(@Valid @RequestBody CreatePostDto createPostDto)
	{
		Post editPost = this.postService.edit(createPostDto);
		
		return ResponseEntity.status(HttpStatus.OK).body(editPost);
	}
	
	@GetMapping
	public ResponseEntity<List<Post>> findAll()
	{
		return ResponseEntity.status(HttpStatus.OK).body(this.postService.findAll());
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<Post>> findAllAuthorPosts()
	{
		return ResponseEntity.status(HttpStatus.OK).body(this.postService.findAllAuthorPosts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Post> findById(@PathVariable Long id)
	{
		return ResponseEntity.status(HttpStatus.OK).body(this.postService.findById(id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Post> deleteById(@PathVariable Long id)
	{
		this.postService.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}