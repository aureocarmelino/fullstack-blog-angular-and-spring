package com.aureocarmelino.imperblogbackend.security.token;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import com.aureocarmelino.imperblogbackend.security.AuthorSystem;

public class CustomTokenEncher implements TokenEnhancer
{
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) 
	{
		AuthorSystem authorSystem = (AuthorSystem) authentication.getPrincipal();
		
		Map<String, Object> info = new HashMap<>();
		
		info.put("logged", authorSystem.getAuthor());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}	
}