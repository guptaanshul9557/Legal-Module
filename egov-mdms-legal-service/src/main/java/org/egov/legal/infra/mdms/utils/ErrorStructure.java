package org.egov.legal.infra.mdms.utils;

import org.springframework.stereotype.Component;

@Component
public class ErrorStructure {
    private int statuscode;
    private String errorMessage;
    private Object rootCause;

    public int getStatuscode() {
        return statuscode;
    }

    // Ensures method chaining by returning the current object
    public ErrorStructure setStatuscode(int statuscode) {
        this.statuscode = statuscode;
        return this;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    // Ensures method chaining by returning the current object
    public ErrorStructure setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public Object getRootCause() {
        return rootCause;
    }

    // Ensures method chaining by returning the current object
    public ErrorStructure setRootCause(Object rootCause) {
        this.rootCause = rootCause;
        return this;
    }
}
