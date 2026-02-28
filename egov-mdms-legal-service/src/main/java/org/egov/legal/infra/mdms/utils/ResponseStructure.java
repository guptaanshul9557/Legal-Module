package org.egov.legal.infra.mdms.utils;

import org.springframework.stereotype.Component;

@Component
public class ResponseStructure<T> {
    private int statuscode;
    private String message;
    private T data;

    public int getStatuscode() {
        return statuscode;
    }

    // Ensures method chaining by returning the current object
    public ResponseStructure<T> setStatuscode(int statuscode) {
        this.statuscode = statuscode;
        return this;
    }

    public String getMessage() {
        return message;
    }

    // Ensures method chaining by returning the current object
    public ResponseStructure<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    // Ensures method chaining by returning the current object
    public ResponseStructure<T> setData(T data) {
        this.data = data;
        return this;
    }
}
