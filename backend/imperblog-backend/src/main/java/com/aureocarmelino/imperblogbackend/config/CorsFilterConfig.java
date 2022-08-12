package com.aureocarmelino.imperblogbackend.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilterConfig implements Filter
{
	@Value("${imperblog.allowedorigin}")
	private String allowedOrigin; 			
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException 
	{
		 HttpServletRequest newRequest = (HttpServletRequest) request;
		 
		 HttpServletResponse newResponse = (HttpServletResponse) response;
		 
		 newResponse.setHeader("Access-Control-Allow-Origin", allowedOrigin);
		 newResponse.setHeader("Access-Control-Allow-Credentials", "true");
		 
		 if ("OPTIONS".equalsIgnoreCase(newRequest.getMethod())  && allowedOrigin.equals(newRequest.getHeader("Origin")))
		 {
			 newResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			 newResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			 newResponse.setHeader("Access-Control-Max-Age", "3600"); // one hour
			 newResponse.setStatus(HttpServletResponse.SC_OK);
		 }
		 else
		 {
			 chain.doFilter(request, response);
		 }
	}
}