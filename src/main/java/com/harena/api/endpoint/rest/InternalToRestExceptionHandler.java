package com.harena.api.endpoint.rest;

import com.harena.api.endpoint.rest.model.Exception;
import com.harena.api.model.exception.BadRequestException;
import com.harena.api.model.exception.ForbiddenException;
import com.harena.api.model.exception.NotFoundException;
import java.nio.file.AccessDeniedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class InternalToRestExceptionHandler {
  @ExceptionHandler(value = {BadRequestException.class})
  ResponseEntity<Exception> handleBadRequest(BadRequestException e) {
    log.info("Bad request", e);
    return new ResponseEntity<>(toRest(e, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {NotFoundException.class})
  ResponseEntity<Exception> handleNotFound(NotFoundException e) {
    log.info("Not found", e);
    return new ResponseEntity<>(toRest(e, HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(value = {java.lang.Exception.class})
  ResponseEntity<Exception> handleDefault(java.lang.Exception e) {
    log.error("Internal error", e);
    return new ResponseEntity<>(
        toRest(e, HttpStatus.INTERNAL_SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = {AccessDeniedException.class, ForbiddenException.class})
  ResponseEntity<Exception> handleForbidden(java.lang.Exception e) {
    /* rest.model.Exception.Type.FORBIDDEN designates both authentication and authorization errors.
     * Hence do _not_ HttpsStatus.UNAUTHORIZED because, counter-intuitively,
     * it's just for authentication.
     * https://stackoverflow.com/questions/3297048/403-forbidden-vs-401-unauthorized-http-responses */
    log.info("Forbidden", e);
    var restException = new Exception();
    restException.setType(HttpStatus.FORBIDDEN.toString());
    restException.setMessage(e.getMessage());
    return new ResponseEntity<>(restException, HttpStatus.FORBIDDEN);
  }

  private Exception toRest(java.lang.Exception e, HttpStatus notFound) {
    var restException = new Exception();
    restException.setType(HttpStatus.BAD_REQUEST.toString());
    restException.setMessage(e.getMessage());
    return restException;
  }
}
