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

package io.service84.library.standardservice.api.rest.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.service84.library.standardservice.api.rest.controllers.ReadinessController;
import io.service84.library.standardservice.services.ReadinessService;

@ExtendWith(SpringExtension.class)
public class ReadinessControllerTests {
  @TestConfiguration
  public static class Configuration {
    @Bean
    public ReadinessController getReadinessController() {
      return new ReadinessController();
    }

    @Bean
    public ReadinessService getReadinessService() {
      return mock(ReadinessService.class);
    }
  }

  // Test Subject
  @Autowired private ReadinessController readinessController;

  @Autowired private ReadinessService mockReadinessService;

  @BeforeEach
  public void setup() {
    reset(mockReadinessService);
  }

  @Test
  public void exists() {
    assertNotNull(readinessController);
    assertNotNull(mockReadinessService);
  }

  @Test
  public void ready() {
    when(mockReadinessService.isReady()).thenReturn(Boolean.TRUE);
    ResponseEntity<Void> readyResponse = readinessController.isReady();
    assertEquals(HttpStatus.OK, readyResponse.getStatusCode());
  }

  @Test
  public void unready() {
    when(mockReadinessService.isReady()).thenReturn(Boolean.FALSE);
    ResponseEntity<Void> readyResponse = readinessController.isReady();
    assertEquals(HttpStatus.SERVICE_UNAVAILABLE, readyResponse.getStatusCode());
  }
}
