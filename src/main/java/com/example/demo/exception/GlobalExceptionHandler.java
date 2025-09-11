package com.example.demo.exception;

import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public InvalidDataMessageException handlerArgumentNotValid(MethodArgumentNotValidException exception) {
    String errorMessage = exception.getBindingResult().getFieldErrors().stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(" | "));

    return new InvalidDataMessageException(errorMessage);
  }

  @ExceptionHandler(ResponseStatusException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseException exceptionHandler(Exception exception) {
    return new ResponseException(exception.getMessage());
  }

  @ExceptionHandler(InvalidDataMessageException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseException invalidDataExceptionHandler(RuntimeException exception) {
    return new ResponseException(exception.getMessage());
  }

  @ExceptionHandler(InvalidCompanyIdException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseException invalidCompanyIdExceptionHandler(RuntimeException exception) {
    return new ResponseException(exception.getMessage());
  }
}