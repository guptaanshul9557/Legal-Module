package org.egov.legal.exceptions_handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	 private static final Logger log =
	            LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {
		
		log.warn("Resource not found | path={} | message={}",
                request.getRequestURI(), ex.getMessage());

        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Resource Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
	
	 @ExceptionHandler(ValidationException.class)
	    public ResponseEntity<ErrorResponse> handleValidation(
	            ValidationException ex,
	            HttpServletRequest request) {
		 
		 log.warn("Validation failed | path={} | message={}",
	                request.getRequestURI(), ex.getMessage());

		 ErrorResponse error = new ErrorResponse(
	                HttpStatus.BAD_REQUEST.value(),
	                "Validation Error",
	                ex.getMessage(),
	                request.getRequestURI()
	        );

	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobal(
            Exception ex,
            HttpServletRequest request) {

		ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                ex.getMessage(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
