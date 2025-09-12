package com.pgr.restapi.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApiResponse<T> {
	private boolean success;
	private String message;
	private T data;
	private LocalDateTime timestamp;
	private String path;

	public ApiResponse() {
		this.timestamp = LocalDateTime.now();
	}

	public ApiResponse(boolean success, String message, T data) {
		this();
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public static <T> ApiResponse<T> success(T data) {
		return new ApiResponse<>(true, "Operation successful", data);
	}

	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<>(true, message, data);
	}

	public static <T> ApiResponse<T> error(String message) {
		return new ApiResponse<>(false, message, null);
	}
}