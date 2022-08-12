package com.aureocarmelino.imperblogbackend.security.oauth2;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.aureocarmelino.imperblogbackend.config.SecretKey;
import com.aureocarmelino.imperblogbackend.security.token.CustomTokenEncher;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter
{
	private AuthenticationManager authenticationManager;
	
	private PasswordEncoder passwordEncoder;
	
	private UserDetailsService userDetailsService;
	
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception 
	{
		 clients.inMemory()
		 	.withClient("teste")
		 	.secret(passwordEncoder.encode("teste"))
		 	.scopes("read", "write")
		 	.authorizedGrantTypes("password", "refresh_token")
		 	.accessTokenValiditySeconds(3600)
		 	.refreshTokenValiditySeconds(3600*2);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception 
	{
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(tokenEnhancer(), accessTokenConverter()));
		
		endpoints 
		 	.tokenStore(tokenStore())
			.tokenEnhancer(tokenEnhancerChain)
			.reuseRefreshTokens(false)
			.userDetailsService(userDetailsService)
			.authenticationManager(authenticationManager);
	}
	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() 
	{
		JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();

		accessTokenConverter.setSigningKey(SecretKey.KEY_TEST);
		accessTokenConverter.setVerifierKey(SecretKey.KEY_TEST);

		return accessTokenConverter;
	}
	
	@Bean
	public TokenStore tokenStore()
	{
		return new JwtTokenStore(accessTokenConverter());
	}	
	
	@Bean
	public TokenEnhancer tokenEnhancer()
	{
		return new CustomTokenEncher();
	}
}