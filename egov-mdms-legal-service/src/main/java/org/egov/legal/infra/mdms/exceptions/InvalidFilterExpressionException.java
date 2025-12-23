package org.egov.legal.infra.mdms.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor
@Getter
public class InvalidFilterExpressionException extends RuntimeException {
    public InvalidFilterExpressionException(String message) {
        super(message);
    }

    public InvalidFilterExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
