package com.aureocarmelino.imperblogbackend.security;

import static com.aureocarmelino.imperblogbackend.util.constant.ConstantAuthentication.ERROR_EMAIL_NOT_FOUND;
import static com.aureocarmelino.imperblogbackend.util.constant.ConstantAuthentication.ERROR_USERNAME_OR_PASSWORD_INVALID;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.aureocarmelino.imperblogbackend.api.repository.AuthorRepository;
import com.aureocarmelino.imperblogbackend.model.entity.Author;
import com.aureocarmelino.imperblogbackend.model.exception.BusinessException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class AuthorDetailsServiceImpl implements UserDetailsService
{
	private final AuthorRepository authorRepository;
	
	private final ModelMapper modelMapper;

	private Map<String, Object> jsonMap; 

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException , BusinessException
	{
		 Optional<Author> authorOptional = authorRepository.findByEmail(email);
		 
		 Author author = authorOptional.orElseThrow(()-> new UsernameNotFoundException(ERROR_USERNAME_OR_PASSWORD_INVALID));
		
		 return new AuthorSystem(author , getPermission(author));
	}

	private Collection<? extends GrantedAuthority> getPermission(Author author) 
	{
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		 
		authorities.add(new SimpleGrantedAuthority("AUTHORITIES"));
		
		return authorities;
	}
	
	public Author findByEmail(String email) throws BusinessException 
    {
        return this.authorRepository.findByEmail(email).orElseThrow(() ->
                new BusinessException(ERROR_EMAIL_NOT_FOUND));
    }
	
	public Author logged() throws BusinessException 
    {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String token = request.getHeader("Authorization").split(" ")[1];
		 
		
        java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
        String[] parts = token.split("\\."); // split out the "parts" (header, payload and signature)
        String payloadJson = new String(decoder.decode(parts[1]));

        
        Map<String, Object> jsonMap =  convertJsonToMap(payloadJson);
        Author author = modelMapper.map(jsonMap.get("logged"), Author.class);
		
		return author; 
    }
	
	public Map<String, Object> convertJsonToMap(String jsonString) 
	{
		ObjectMapper objectMapper = new ObjectMapper();

        try 
        {
            this.jsonMap = objectMapper.readValue(jsonString,new TypeReference<Map<String, Object>>() {});
            return this.jsonMap;
        } 
        catch (Exception ex) 
        {
            throw new RuntimeException(ex);
        }
    }
	
	public String passwordEncode(String palavraPasse) 
    {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(palavraPasse);
    }
	
}