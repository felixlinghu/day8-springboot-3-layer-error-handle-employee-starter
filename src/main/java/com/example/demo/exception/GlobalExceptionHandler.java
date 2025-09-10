package com.example.demo.exception;

import com.example.demo.exception.ResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

public class GlobalExceptionHandler {
  @ExceptionHandler(ResponseStatusException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseException exceptionHandler(Exception exception) {
    return new ResponseException(exception.getMessage());
  }
}