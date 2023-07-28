package com.Infopeer.Blogging_Application.exceptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Infopeer.Blogging_Application.payloads.AppResponce;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<AppResponce> handleResourceNotFoundException(ResourceNotFoundException ex)
	{
		String message = ex.getMessage();
		AppResponce appResponce=new AppResponce();
		appResponce.setMessage(message);
		appResponce.setSuccess(false);
		appResponce.setLocalDateTime(LocalDateTime.now());
		
		return new ResponseEntity<AppResponce>(appResponce , HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException mex)
   {
	Map<String, String> responce=new HashMap<>();
		mex.getBindingResult().getAllErrors().forEach((error) ->{
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			responce.put(fieldName, message);
		});
		
		return new ResponseEntity<Map<String,String>>(responce , HttpStatus.BAD_REQUEST);
	   
   }

}
