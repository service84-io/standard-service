package io.service84.library.standardservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class NoContributorMetricsServiceTests {
  @TestConfiguration
  public static class Configuration {

    @Bean
    public MetricsService getMetricsService() {
      return new MetricsService();
    }
  }

  // Test Subject
  @Autowired private MetricsService metricsService;

  @Test
  public void exists() {
    assertNotNull(metricsService);
  }

  @Test
  public void getMetrics() {
    assertEquals(Collections.emptyMap(), metricsService.getMetrics());
  }
}
