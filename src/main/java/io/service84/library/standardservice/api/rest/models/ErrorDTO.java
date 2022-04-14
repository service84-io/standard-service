package io.service84.library.standardservice.api.rest.models;

public class ErrorDTO {
  public java.time.LocalDateTime timestamp;
  public Integer status;
  public String error;
  public String message;
  public String path;

  public java.time.LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(java.time.LocalDateTime timestamp) {
    this.timestamp = timestamp;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}
