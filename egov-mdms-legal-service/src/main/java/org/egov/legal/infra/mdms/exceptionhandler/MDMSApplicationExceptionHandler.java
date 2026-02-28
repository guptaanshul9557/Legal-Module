package org.egov.legal.infra.mdms.exceptionhandler;

import org.egov.legal.infra.mdms.exceptions.FileReadingException;
import org.egov.legal.infra.mdms.exceptions.InvalidTenantIdException;
import org.egov.legal.infra.mdms.exceptions.JSONPARSEException;
import org.egov.legal.infra.mdms.exceptions.ModuleNotFoundException;
import org.egov.legal.infra.mdms.utils.ErrorStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class MDMSApplicationExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(MDMSApplicationExceptionHandler.class);

    private final ErrorStructure errorStructure;
    

    @Autowired
    public MDMSApplicationExceptionHandler(ErrorStructure errorStructure) {
        this.errorStructure = errorStructure;
    }

    // Common error response structure
    private ResponseEntity<ErrorStructure> errorResponse(HttpStatus status, String errorMessage, Object rootCause) {
        return new ResponseEntity<>(errorStructure.setStatuscode(status.value())
                .setErrorMessage(errorMessage)
                .setRootCause(rootCause), status);
    }

    @ExceptionHandler(InvalidTenantIdException.class)
    public ResponseEntity<ErrorStructure> handleInvalidTenantId(InvalidTenantIdException ex) {
        log.error("Invalid Tenant ID: {}", ex.getMessage());
        return errorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "Invalid Tenant Id");
    }

    @ExceptionHandler(ModuleNotFoundException.class)
    public ResponseEntity<ErrorStructure> handleModuleNotFound(ModuleNotFoundException ex) {
        log.error("Module Not Found: {}", ex.getMessage());
        return errorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "Requested module not found");
    }

    @ExceptionHandler(JSONPARSEException.class)
    public ResponseEntity<ErrorStructure> handleJsonParseError(JSONPARSEException ex) {
        log.error("JSON Parsing Error: {}", ex.getMessage());
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid JSON format", ex.getMessage());
    }

    @ExceptionHandler(FileReadingException.class)
    public ResponseEntity<ErrorStructure> handleFileReadingError(FileReadingException ex) {
        log.error("File Reading Error: {}", ex.getMessage());
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading file", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorStructure> handleGenericException(Exception ex) {
        log.error("Unexpected Error: {}", ex.getMessage());
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", ex.getMessage());
    }
}
