package org.egov.legal.infra.mdms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor
@Getter
public class FileReadingException extends RuntimeException {
	
    public FileReadingException(String message) {
        super(message);
    }

    public FileReadingException(String message, Throwable cause) {
        super(message, cause);
    }

	    // Constructor with both message and root cause
	   

}
