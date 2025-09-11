package com.example.demo.exception;

public class InvalidDataMessageException extends RuntimeException {

  public InvalidDataMessageException(String message) {
    super(message);
  }
}
