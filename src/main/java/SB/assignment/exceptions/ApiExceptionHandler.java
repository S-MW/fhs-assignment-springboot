package SB.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e)
    {
        // 1. Create payload.
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiExceptions apiExceptions = new ApiExceptions(e.getMessage(),httpStatus, ZonedDateTime.now());

        // 2. Return response entity
        return new ResponseEntity<>(apiExceptions,httpStatus);
    }


    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e)
    {
        // 1. Create payload.
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiExceptions apiExceptions = new ApiExceptions(e.getMessage(),httpStatus, ZonedDateTime.now());

        // 2. Return response entity
        return new ResponseEntity<>(apiExceptions,httpStatus);
    }
}