package com.pgr.restapi.exception;

public class DuplicateUserException extends RuntimeException {
	public DuplicateUserException(String message) {
		super(message);
	}
}