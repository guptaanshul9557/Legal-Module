package org.egov.legal.infra.mdms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor
@Getter
public class JSONPARSEException extends RuntimeException{

    public JSONPARSEException(String message) {
        super(message);
    }

    public JSONPARSEException(String message, Throwable cause) {
        super(message, cause);
    }
}
