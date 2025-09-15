package com.pgr.restapi.exception;

public class InvalidUserDataException extends RuntimeException {
	public InvalidUserDataException(String message) {
		super(message);
	}
}
