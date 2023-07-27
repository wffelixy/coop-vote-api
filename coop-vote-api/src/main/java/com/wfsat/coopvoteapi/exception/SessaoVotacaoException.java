package com.wfsat.coopvoteapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SessaoVotacaoException extends RuntimeException{

	private static final long serialVersionUID = -1644012675040993943L;

	public SessaoVotacaoException(String message) {
        super(message);
    }
	
}
