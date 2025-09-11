package com.example.demo.exception;

public class InvalidCompanyIdException extends RuntimeException {

  public InvalidCompanyIdException(String message) {
    super(message);
  }
}
