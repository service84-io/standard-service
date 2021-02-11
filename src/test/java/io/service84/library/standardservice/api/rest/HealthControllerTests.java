package io.service84.library.standardservice.api.rest;

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
