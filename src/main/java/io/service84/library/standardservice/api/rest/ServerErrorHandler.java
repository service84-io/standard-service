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
