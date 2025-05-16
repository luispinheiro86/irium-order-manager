package com.irium.order.manager.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  private static final ObjectMapper mapper = new ObjectMapper()
	  .setSerializationInclusion(JsonInclude.Include.NON_NULL)
	  .registerModule(new JavaTimeModule()).disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

  @ExceptionHandler(OrderManagerException.class)
  public ResponseEntity<?> handleOrderManagerException(OrderManagerException exception) {
	Map<String, Object> errorDetails = new HashMap<>();
	errorDetails.put("timestamp", LocalDateTime.now());
	errorDetails.put("error", exception.getStatus().getReasonPhrase());
	errorDetails.put("message", exception.getLocalizedMessage());
	logger.error(errorDetails.toString(), exception);
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Map<String, Object>> handleGenericException(Exception exception) {
	Map<String, Object> errorDetails = new HashMap<>();
	errorDetails.put("timestamp", LocalDateTime.now());
	errorDetails.put("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	errorDetails.put("message", exception.getMessage());
	logger.error(errorDetails.toString(), exception);
	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
  }

}
