package io.service84.library.standardservice.api.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.service84.library.standardservice.services.MetricsService;

@ExtendWith(SpringExtension.class)
public class MetricsControllerTests {
  @TestConfiguration
  public static class Configuration {

    @Bean
    public MetricsController getMetricsController() {
      return new MetricsController();
    }

    @Bean
    public MetricsService getMetricsService() {
      return mock(MetricsService.class);
    }
  }

  // Test Subject
  @Autowired private MetricsController metricsController;

  @Autowired private MetricsService mockMetricsService;

  @Test
  public void exists() {
    assertNotNull(metricsController);
    assertNotNull(mockMetricsService);
  }

  @Test
  public void getMetrics() {
    Map<String, Object> metrics = new HashMap<>();
    metrics.put(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    metrics.put(UUID.randomUUID().toString(), UUID.randomUUID());
    when(mockMetricsService.getMetrics()).thenReturn(metrics);
    Map<String, Object> gotMetrics = metricsController.getMetrics();
    assertEquals(metrics, gotMetrics);
  }

  @BeforeEach
  public void setup() {
    reset(mockMetricsService);
  }
}