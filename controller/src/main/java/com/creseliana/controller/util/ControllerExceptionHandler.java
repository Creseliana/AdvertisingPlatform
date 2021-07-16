package com.creseliana.controller.util;

import com.creseliana.service.exception.ad.AccessException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {DataAccessException.class})
    public ResponseEntity<String> handleDataAccessException(DataAccessException e) {
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AccessException.class})
    public ResponseEntity<String> handleAccessException(AccessException e) {
        return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.FORBIDDEN);
    }


}
