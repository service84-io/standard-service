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
public class NoContributorReadinessServiceTests {
  @TestConfiguration
  public static class Configuration {

    @Bean
    public ReadinessService getReadinessService() {
      return new ReadinessService();
    }
  }

  // Test Subject
  @Autowired private ReadinessService readinessService;

  @Test
  public void exists() {
    assertNotNull(readinessService);
  }

  @Test
  public void isReady() {
    assertEquals(Boolean.TRUE, readinessService.isReady());
  }
}
