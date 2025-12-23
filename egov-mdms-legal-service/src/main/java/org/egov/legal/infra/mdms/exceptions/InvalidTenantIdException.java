package org.egov.legal.infra.mdms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor
@Getter
public class InvalidTenantIdException extends RuntimeException {

	
    public  InvalidTenantIdException(String message) {
        super(message);
    }

    public  InvalidTenantIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
