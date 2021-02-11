package io.service84.library.standardservice.api.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ServerErrorHandler {
  public static class ErrorDTO {
    public String message;
  }

  @ExceptionHandler(Error.class)
  @ResponseBody
  public ResponseEntity<ErrorDTO> handleUncaughtError(Error e) {
    ErrorDTO dto = new ErrorDTO();
    dto.message = e.getMessage();
    return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseBody
  public ResponseEntity<ErrorDTO> handleUncaughtRuntimeException(RuntimeException e) {
    ErrorDTO dto = new ErrorDTO();
    dto.message = e.getMessage();
    return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
