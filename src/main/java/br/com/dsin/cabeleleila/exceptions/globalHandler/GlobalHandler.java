package br.com.dsin.cabeleleila.exceptions.globalHandler;

import br.com.dsin.cabeleleila.dto.response.error.ErrorResponse;
import br.com.dsin.cabeleleila.dto.response.error.ErrorValidationResponse;
import br.com.dsin.cabeleleila.exceptions.BusinessRuleException;
import br.com.dsin.cabeleleila.exceptions.ConflictException;
import br.com.dsin.cabeleleila.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFound(ResourceNotFoundException ex) {
        var errorResponse = createErrorResponse(ex.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> conflict(ConflictException ex) {
        var errorResponse = createErrorResponse(ex.getMessage(), 409);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> businessError(BusinessRuleException ex) {
        var errorResponse = createErrorResponse(ex.getMessage(), 422);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorValidationResponse>> validationsError(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.stream()
                .map(error -> new ErrorValidationResponse(error.getField(), error.getDefaultMessage(), 400))
                .toList());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> unexpectedError(Exception ex) {
        var errorResponse = createErrorResponse(ex.getMessage(), 500);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    private ErrorResponse createErrorResponse(String message, int status) {
        return new ErrorResponse(message, status);
    }
}
