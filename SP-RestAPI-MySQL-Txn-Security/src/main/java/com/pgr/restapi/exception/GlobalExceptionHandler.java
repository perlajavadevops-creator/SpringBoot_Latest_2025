package com.pgr.restapi.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.pgr.restapi.dto.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> handleUserNotFoundException(UserNotFoundException ex,
			WebRequest request) {

		ApiResponse<Object> response = ApiResponse.error(ex.getMessage());
		response.setPath(request.getDescription(false));

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<ApiResponse<Object>> handleDuplicateUserException(DuplicateUserException ex,
			WebRequest request) {

		ApiResponse<Object> response = ApiResponse.error(ex.getMessage());
		response.setPath(request.getDescription(false));

		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(
			MethodArgumentNotValidException ex, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		ApiResponse<Map<String, String>> response = new ApiResponse<>(false, "Validation failed", errors);
		response.setPath(request.getDescription(false));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex, WebRequest request) {

		ApiResponse<Object> response = ApiResponse.error("An unexpected error occurred");
		response.setPath(request.getDescription(false));

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
