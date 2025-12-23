package org.egov.legal.infra.mdms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor
@Getter
public class MasterDataNotFoundException extends RuntimeException {

	
    public  MasterDataNotFoundException(String message) {
        super(message);
    }

    public  MasterDataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
