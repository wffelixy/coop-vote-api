package com.wfsat.coopvoteapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AssociadoException extends RuntimeException{

	private static final long serialVersionUID = 2139058530412822339L;

	public AssociadoException (String message) {
		 super(message);
	}
}
