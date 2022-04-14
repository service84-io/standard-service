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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.service84.library.standardservice.api.rest.controllers.ServerErrorHandler;
import io.service84.library.standardservice.api.rest.models.ErrorDTO;
import io.service84.library.standardservice.services.RequestService;

@ExtendWith(SpringExtension.class)
public class ServerErrorHandlerTests {
  @TestConfiguration
  public static class Configuration {
    @Bean
    public SmartInstantiationAwareBeanPostProcessor mockBeanAdapter() {
      return new SmartInstantiationAwareBeanPostProcessor() {
        @Override
        public boolean postProcessAfterInstantiation(Object bean, String beanName)
            throws BeansException {
          return !mockingDetails(bean).isMock();
        }
      };
    }

    @Bean
    public ServerErrorHandler getServerErrorHandler() {
      return new ServerErrorHandler();
    }

    @Bean
    public RequestService getReadinessService() {
      return mock(RequestService.class);
    }
  }

  // Test Subject
  @Autowired private ServerErrorHandler serverErrorHandler;

  @Autowired private RequestService mockRequestService;

  @BeforeEach
  public void setup() {
    reset(mockRequestService);
  }

  @Test
  public void handleUncaughtError() {
    HttpStatus expectedStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = UUID.randomUUID().toString();
    String path = "/" + UUID.randomUUID().toString();
    when(mockRequestService.getPath()).thenReturn(path);
    Error error = new Error(message);
    LocalDateTime before = LocalDateTime.now();
    ResponseEntity<ErrorDTO> responseWrapper = serverErrorHandler.handleUncaughtError(error);
    LocalDateTime after = LocalDateTime.now();
    assertEquals(expectedStatus, responseWrapper.getStatusCode());
    ErrorDTO dto = responseWrapper.getBody();
    assertTrue(dto.timestamp.isAfter(before));
    assertTrue(dto.timestamp.isBefore(after));
    assertEquals(expectedStatus.value(), dto.status);
    assertEquals(expectedStatus.getReasonPhrase(), dto.error);
    assertEquals(message, dto.message);
    assertEquals(path, dto.path);
  }

  @Test
  public void handleUncaughtRuntimeException() {
    HttpStatus expectedStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    String message = UUID.randomUUID().toString();
    String path = "/" + UUID.randomUUID().toString();
    when(mockRequestService.getPath()).thenReturn(path);
    RuntimeException exception = new RuntimeException(message);
    LocalDateTime before = LocalDateTime.now();
    ResponseEntity<ErrorDTO> responseWrapper =
        serverErrorHandler.handleUncaughtRuntimeException(exception);
    LocalDateTime after = LocalDateTime.now();
    assertEquals(expectedStatus, responseWrapper.getStatusCode());
    ErrorDTO dto = responseWrapper.getBody();
    assertTrue(dto.timestamp.isAfter(before));
    assertTrue(dto.timestamp.isBefore(after));
    assertEquals(expectedStatus.value(), dto.status);
    assertEquals(expectedStatus.getReasonPhrase(), dto.error);
    assertEquals(message, dto.message);
    assertEquals(path, dto.path);
  }
}
