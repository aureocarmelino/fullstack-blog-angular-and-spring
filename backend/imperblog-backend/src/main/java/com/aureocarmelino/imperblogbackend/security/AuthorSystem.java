package com.aureocarmelino.imperblogbackend.security;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.aureocarmelino.imperblogbackend.model.entity.Author;
import lombok.Getter;

@Getter
public class AuthorSystem extends User
{
	private static final long serialVersionUID = 1L;
	private Author author; 
	
	public AuthorSystem(Author author, Collection<? extends GrantedAuthority> authorities)
	{	
		super(author.getEmail(), author.getPassword(), authorities);
		this.author = author;
	}
}