package com.fajar.personalcontact.exception;

import com.fajar.personalcontact.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmptyContactListException.class)
    public ResponseEntity<Object> handleEmptyContactListException(EmptyContactListException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error" ,ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Object> handleInvalidEmailException(InvalidEmailException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(DuplicateDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleDuplicateDataException(DuplicateDataException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(NoContactsFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleNoContactsFoundException(NoContactsFoundException ex) {
        return ex.getMessage();
    }


}
