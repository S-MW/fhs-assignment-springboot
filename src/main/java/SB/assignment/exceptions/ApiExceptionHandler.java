package SB.assignment.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiExceptions apiExceptions = new ApiExceptions(e.getMessage(), httpStatus, ZonedDateTime.now());

        log.error("handleApiRequestException - started with error: " + apiExceptions.getMessage());
        return new ResponseEntity<>(apiExceptions, httpStatus);
    }


    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ApiExceptions apiExceptions = new ApiExceptions(e.getMessage(), httpStatus, ZonedDateTime.now());

        log.error("handleUnauthorizedException - started with error: " + apiExceptions.getMessage());
        return new ResponseEntity<>(apiExceptions, httpStatus);
    }
}