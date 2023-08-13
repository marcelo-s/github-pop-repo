package com.shopapotheke.githubpoprepo.adapter.in.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler({InputException.class, MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ApplicationError> handleInputException(Exception ex) {
        String errorMessage = "Bad input: %s".formatted(ex.getMessage());
        log.error(errorMessage, ex);
        return new ResponseEntity<>(new ApplicationError(HttpStatus.BAD_REQUEST.toString(), errorMessage),
                                    HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApplicationError> handleGenericException(Exception ex) {
        String errorMessage = "An unexpected error occurred.";
        log.error(errorMessage, ex);
        return new ResponseEntity<>(new ApplicationError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), errorMessage),
                                    HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
