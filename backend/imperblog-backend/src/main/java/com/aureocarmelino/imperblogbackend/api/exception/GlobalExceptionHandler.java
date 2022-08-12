package com.aureocarmelino.imperblogbackend.api.exception;

import static com.aureocarmelino.imperblogbackend.util.constant.ConstantGlobalExceptionHandler.ERROR_ONE_OR_MORE_FILDS_ARE_INVALID;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.aureocarmelino.imperblogbackend.model.exception.BusinessException;
import com.aureocarmelino.imperblogbackend.model.exception.ObjectNotFoundException;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(UnauthorizedUserException.class)
	public ResponseEntity<Object> handleUnauthorizedClientException(UnauthorizedUserException ex, WebRequest request) 
	{
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		
		Problem problem = getProblem(status.value(), ex.getMessage(), null, request);
		
		return handleExceptionInternal(ex, problem, new org.springframework.http.HttpHeaders(), status, request);
	}
	
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) 
	{
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		Problem problem = getProblem(status.value(), ex.getMessage(), null, request);
		
		return handleExceptionInternal(ex, problem, new org.springframework.http.HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<Object> handleObjectNotFoundExceptionException(ObjectNotFoundException ex, WebRequest request) 
	{
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problem problem = getProblem(status.value(), ex.getMessage(), null, request);
		
		return handleExceptionInternal(ex, problem, new org.springframework.http.HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<Object> handleBusinessException(BusinessException ex, WebRequest request) 
	{
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problem problem = getProblem(status.value(), ex.getMessage(), null,request);
		
		return handleExceptionInternal(ex, problem, new org.springframework.http.HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			org.springframework.http.HttpHeaders headers, HttpStatus status, WebRequest request) 
	{
		
		List<Field> fieldErrors = new ArrayList<Field>();
		
		for(ObjectError error : ex.getBindingResult().getAllErrors()) 
		{
			String name = ((FieldError) error).getField();
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			
			fieldErrors.add(new Field(name, message));
		}
		
		Problem problem = getProblem(
				status.value(), 
				ERROR_ONE_OR_MORE_FILDS_ARE_INVALID, 
				fieldErrors, request);
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private Problem getProblem(Integer status, String title, List<Field> fields, WebRequest request) 
	{
		return new Problem(status, OffsetDateTime.now(), title, fields, ((ServletWebRequest)request).getRequest().getRequestURI().toString());
	}
}