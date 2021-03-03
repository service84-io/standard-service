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

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.service84.library.standardservice.services.MetricsService.MetricsContributor;

@ExtendWith(SpringExtension.class)
public class MetricsServiceTests {
  @TestConfiguration
  public static class Configuration {

    @Bean
    public MetricsService getMetricsService() {
      return new MetricsService();
    }
  }

  // Test Subject
  @Autowired private MetricsService metricsService;

  private MetricsContributor metricsContributorA = mock(MetricsContributor.class);
  private MetricsContributor metricsContributorB = mock(MetricsContributor.class);

  @Test
  public void bothFound() {
    String keyA = UUID.randomUUID().toString();
    String valueA = UUID.randomUUID().toString();
    String keyB = UUID.randomUUID().toString();
    UUID valueB = UUID.randomUUID();
    Map<String, Object> metrics = new HashMap<>();
    metrics.put(keyA, valueA);
    metrics.put(keyB, valueB);
    when(metricsContributorA.key()).thenReturn(keyA);
    when(metricsContributorA.value()).thenReturn(valueA);
    when(metricsContributorB.key()).thenReturn(keyB);
    when(metricsContributorB.value()).thenReturn(valueB);
    Map<String, Object> gotMetrics = metricsService.getMetrics();
    assertEquals(metrics, gotMetrics);
  }

  @Test
  public void exists() {
    assertNotNull(metricsService);
    assertNotNull(metricsContributorA);
    assertNotNull(metricsContributorB);
  }

  @Test
  public void keyThrowOmit() {
    String keyA = UUID.randomUUID().toString();
    String valueA = UUID.randomUUID().toString();
    UUID valueB = UUID.randomUUID();
    Map<String, Object> metrics = new HashMap<>();
    metrics.put(keyA, valueA);
    when(metricsContributorA.key()).thenReturn(keyA);
    when(metricsContributorA.value()).thenReturn(valueA);
    when(metricsContributorB.key()).thenThrow(new RuntimeException(UUID.randomUUID().toString()));
    when(metricsContributorB.value()).thenReturn(valueB);
    Map<String, Object> gotMetrics = metricsService.getMetrics();
    assertEquals(metrics, gotMetrics);
  }

  @BeforeEach
  public void setup() {
    reset(metricsContributorA);
    reset(metricsContributorB);
    metricsService.registerMetricsContributor(metricsContributorA);
    metricsService.registerMetricsContributor(metricsContributorB);
  }

  @AfterEach
  public void tearDown() {
    metricsService.unregisterReadinessContributor(metricsContributorA);
    metricsService.unregisterReadinessContributor(metricsContributorB);
  }

  @Test
  public void valueThrowNull() {
    String keyA = UUID.randomUUID().toString();
    String valueA = UUID.randomUUID().toString();
    String keyB = UUID.randomUUID().toString();
    Map<String, Object> metrics = new HashMap<>();
    metrics.put(keyA, valueA);
    metrics.put(keyB, null);
    when(metricsContributorA.key()).thenReturn(keyA);
    when(metricsContributorA.value()).thenReturn(valueA);
    when(metricsContributorB.key()).thenReturn(keyB);
    when(metricsContributorB.value()).thenThrow(new RuntimeException(UUID.randomUUID().toString()));
    Map<String, Object> gotMetrics = metricsService.getMetrics();
    assertEquals(metrics, gotMetrics);
  }
}
