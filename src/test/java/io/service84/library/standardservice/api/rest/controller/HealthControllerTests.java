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

import io.service84.library.standardservice.api.rest.controller.HealthController;
import io.service84.library.standardservice.services.HealthService;

@ExtendWith(SpringExtension.class)
public class HealthControllerTests {
  @TestConfiguration
  public static class Configuration {

    @Bean
    public HealthController getHealthController() {
      return new HealthController();
    }

    @Bean
    public HealthService getHealthService() {
      return mock(HealthService.class);
    }
  }

  // Test Subject
  @Autowired private HealthController healthController;

  @Autowired private HealthService mockHealthService;

  @Test
  public void exists() {
    assertNotNull(healthController);
    assertNotNull(mockHealthService);
  }

  @Test
  public void healthy() {
    when(mockHealthService.isHealthy()).thenReturn(Boolean.TRUE);
    ResponseEntity<Void> healthResponse = healthController.isHealthy();
    assertEquals(HttpStatus.OK, healthResponse.getStatusCode());
  }

  @BeforeEach
  public void setup() {
    reset(mockHealthService);
  }

  @Test
  public void unhealthy() {
    when(mockHealthService.isHealthy()).thenReturn(Boolean.FALSE);
    ResponseEntity<Void> healthResponse = healthController.isHealthy();
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, healthResponse.getStatusCode());
  }
}
