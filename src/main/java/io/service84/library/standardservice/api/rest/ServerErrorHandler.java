package io.service84.library.standardservice.api.rest;

import javax.servlet.http.HttpServletRequest;

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
  public ResponseEntity<Object> handleExceptionalResultException(
      HttpServletRequest request, Error ex) {
    ErrorDTO dto = new ErrorDTO();
    dto.message = ex.getMessage();
    return new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
