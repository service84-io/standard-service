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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.service84.library.standardservice.api.rest.ServerErrorHandler.ErrorDTO;

@ExtendWith(SpringExtension.class)
public class ServerErrorHandlerTests {
  @TestConfiguration
  public static class Configuration {

    @Bean
    public ServerErrorHandler getServerErrorHandler() {
      return new ServerErrorHandler();
    }
  }

  // Test Subject
  @Autowired private ServerErrorHandler serverErrorHandler;

  @Test
  public void handleUncaughtError() {
    String message = UUID.randomUUID().toString();
    Error error = new Error(message);
    ResponseEntity<ErrorDTO> responseWrapper = serverErrorHandler.handleUncaughtError(error);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseWrapper.getStatusCode());
    assertEquals(message, responseWrapper.getBody().message);
  }

  @Test
  public void handleUncaughtRuntimeException() {
    String message = UUID.randomUUID().toString();
    RuntimeException exception = new RuntimeException(message);
    ResponseEntity<ErrorDTO> responseWrapper =
        serverErrorHandler.handleUncaughtRuntimeException(exception);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseWrapper.getStatusCode());
    assertEquals(message, responseWrapper.getBody().message);
  }
}
