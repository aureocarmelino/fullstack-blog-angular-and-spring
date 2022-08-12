package com.aureocarmelino.imperblogbackend.api.service;

import static com.aureocarmelino.imperblogbackend.util.constant.ConstantPost.ERROR_POST_DOESNT_BELONG_LOGGED_USER;
import static com.aureocarmelino.imperblogbackend.util.constant.ConstantPost.ERROR_POST_NOT_FOUND;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aureocarmelino.imperblogbackend.api.repository.PostRepository;
import com.aureocarmelino.imperblogbackend.model.dto.postDto.CreatePostDto;
import com.aureocarmelino.imperblogbackend.model.entity.Post;
import com.aureocarmelino.imperblogbackend.model.exception.BusinessException;
import com.aureocarmelino.imperblogbackend.model.exception.ObjectNotFoundException;
import com.aureocarmelino.imperblogbackend.security.AuthorDetailsServiceImpl;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class PostService 
{
	private ModelMapper modelMapper;
	
	private PostRepository postRepository;
	
	private AuthorDetailsServiceImpl authorDetailsServiceImpl;

	@Transactional
	public Post create(CreatePostDto createPostDto)
	{
		Post newPost = modelMapper.map(createPostDto, Post.class);
		
		newPost.setAuthor(authorDetailsServiceImpl.logged());
		return postRepository.save(newPost);
	}
	
	public List<Post> findAll()
	{
		return postRepository.findAll();
	}
	
	public List<Post> findAllAuthorPosts()
	{
		return postRepository.findAllAuthorPosts(authorDetailsServiceImpl.logged());
	}
	
	public Post findById(Long id)
	{
		return this.postRepository.findById(id).orElseThrow(() -> 
			new ObjectNotFoundException(ERROR_POST_NOT_FOUND));
	}
	
	
	@Transactional
	public Post edit(CreatePostDto createPostDto)
	{
		Post editedPost = this.findById(createPostDto.getPkPost());
		
		BeanUtils.copyProperties(createPostDto, editedPost, "pkPost");
		editedPost.setAuthor(authorDetailsServiceImpl.logged());
		return postRepository.save(editedPost);
	}
	

	@Transactional
	public void deleteById(Long id)
	{
		Post post = findById(id);
		
		if(!post.getAuthor().equals(authorDetailsServiceImpl.logged()))
			throw new BusinessException(ERROR_POST_DOESNT_BELONG_LOGGED_USER);
		
		
		this.postRepository.deleteById(id);
	}
}