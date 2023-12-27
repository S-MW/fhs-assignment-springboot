package SB.assignment.validation;

import SB.assignment.exceptions.ApiExceptions;
import SB.assignment.exceptions.ApiRequestException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        return super.handleMethodArgumentNotValid(ex, headers, status, request);
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        System.out.println("handleMethodArgumentNotValid - handleMethodArgumentNotValid - handleMethodArgumentNotValid");
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->
        {
            String filedName = ((FieldError) error).getField();
            String massage = error.getDefaultMessage();
            errors.put(filedName, massage);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {
        System.out.println("handleConstraintViolationException - handleConstraintViolationException - handleConstraintViolationException");
        return new ResponseEntity<Object>(new ApiExceptions(ex.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public  ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        System.out.println("handleTypeMismatch - handleTypeMismatch - handleTypeMismatch");
        return new ResponseEntity<Object>(new ApiExceptions(ex.getMessage(),HttpStatus.BAD_REQUEST, ZonedDateTime.now()),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        System.out.println("handleAll - handleAll - handleAll");
        return new ResponseEntity<Object>(new ApiExceptions(ex.getMessage(), HttpStatus.BAD_REQUEST, ZonedDateTime.now()), HttpStatus.BAD_REQUEST);
    }


}