package com.wfsat.coopvoteapi.exception;


public class PautaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3705372659331076820L;

	public PautaNotFoundException(String message) {
        super(message);
    }
}
