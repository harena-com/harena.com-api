package com.harena.api.model.exception;

public class InternalServerErrorException extends ApiException {
  public InternalServerErrorException(Exception source) {
    super(ExceptionType.SERVER_EXCEPTION, source);
  }
}
