/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.service84.library.standardservice.api.rest.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import io.service84.library.standardservice.api.rest.model.ErrorDTO;
import io.service84.library.standardservice.services.RequestService;

@ControllerAdvice
public class ServerErrorHandler {
  private static final Logger logger = LoggerFactory.getLogger(ServerErrorHandler.class);

  private @Autowired RequestService requestService;

  @ExceptionHandler(Error.class)
  @ResponseBody
  public ResponseEntity<ErrorDTO> handleUncaughtError(Error e) {
    logger.debug("handleUncaughtError");
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ErrorDTO dto = new ErrorDTO();
    dto.setTimestamp(LocalDateTime.now());
    dto.setStatus(status.value());
    dto.setError(status.getReasonPhrase());
    dto.setMessage(e.getMessage());
    dto.setPath(requestService.getPath());
    return new ResponseEntity<>(dto, status);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseBody
  public ResponseEntity<ErrorDTO> handleUncaughtRuntimeException(RuntimeException e) {
    logger.debug("handleUncaughtRuntimeException");
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    ErrorDTO dto = new ErrorDTO();
    dto.setTimestamp(LocalDateTime.now());
    dto.setStatus(status.value());
    dto.setError(status.getReasonPhrase());
    dto.setMessage(e.getMessage());
    dto.setPath(requestService.getPath());
    return new ResponseEntity<>(dto, status);
  }
}
