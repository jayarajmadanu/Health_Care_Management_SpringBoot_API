package com.examly.healthCareManagement.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvAuthException extends RuntimeException{
	public String msg = "invalid email or password";

	public InvAuthException(String msg) {
		super(msg);
		this.msg = msg;
	}
	
	public InvAuthException() {
		super();
	}
}
