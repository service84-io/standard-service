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

package io.service84.library.standardservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.service84.library.standardservice.services.HealthService.HealthContributor;

@ExtendWith(SpringExtension.class)
public class HealthServiceTests {
  @TestConfiguration
  public static class Configuration {
    @Bean
    public HealthService getHealthService() {
      return new HealthService();
    }
  }

  // Test Subject
  @Autowired private HealthService healthService;

  private HealthContributor healthContributorA = mock(HealthContributor.class);
  private HealthContributor healthContributorB = mock(HealthContributor.class);

  @Test
  public void bothHealthy() {
    when(healthContributorA.isHealthy()).thenReturn(Boolean.TRUE);
    when(healthContributorB.isHealthy()).thenReturn(Boolean.TRUE);
    assertEquals(Boolean.TRUE, healthService.isHealthy());
  }

  @Test
  public void bothUnhealthy() {
    when(healthContributorA.isHealthy()).thenReturn(Boolean.FALSE);
    when(healthContributorB.isHealthy()).thenReturn(Boolean.FALSE);
    assertEquals(Boolean.FALSE, healthService.isHealthy());
  }

  @Test
  public void exists() {
    assertNotNull(healthService);
    assertNotNull(healthContributorA);
    assertNotNull(healthContributorB);
  }

  @BeforeEach
  public void setup() {
    reset(healthContributorA);
    reset(healthContributorB);
    healthService.registerReadinessContributor(healthContributorA);
    healthService.registerReadinessContributor(healthContributorB);
  }

  @AfterEach
  public void tearDown() {
    healthService.unregisterReadinessContributor(healthContributorA);
    healthService.unregisterReadinessContributor(healthContributorB);
  }

  @Test
  public void unhealthyA() {
    when(healthContributorA.isHealthy()).thenReturn(Boolean.FALSE);
    when(healthContributorB.isHealthy()).thenReturn(Boolean.TRUE);
    assertEquals(Boolean.FALSE, healthService.isHealthy());
  }

  @Test
  public void unhealthyB() {
    when(healthContributorA.isHealthy()).thenReturn(Boolean.TRUE);
    when(healthContributorB.isHealthy()).thenReturn(Boolean.FALSE);
    assertEquals(Boolean.FALSE, healthService.isHealthy());
  }

  @Test
  public void unhealthyException() {
    when(healthContributorA.isHealthy()).thenReturn(Boolean.TRUE);
    when(healthContributorB.isHealthy())
        .thenThrow(new RuntimeException(UUID.randomUUID().toString()));
    assertEquals(Boolean.FALSE, healthService.isHealthy());
  }
}
