package io.service84.library.standardservice.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class NoContributorHealthServiceTests {
  @TestConfiguration
  public static class Configuration {

    @Bean
    public HealthService getHealthService() {
      return new HealthService();
    }
  }

  // Test Subject
  @Autowired private HealthService healthService;

  @Test
  public void exists() {
    assertNotNull(healthService);
  }

  @Test
  public void isHealthy() {
    assertEquals(Boolean.TRUE, healthService.isHealthy());
  }
}
