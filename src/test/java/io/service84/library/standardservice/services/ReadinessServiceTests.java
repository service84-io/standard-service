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

import io.service84.library.standardservice.services.ReadinessService.ReadinessContributor;

@ExtendWith(SpringExtension.class)
public class ReadinessServiceTests {
  @TestConfiguration
  public static class Configuration {
    @Bean
    public ReadinessService getReadinessService() {
      return new ReadinessService();
    }
  }

  // Test Subject
  @Autowired private ReadinessService readinessService;

  private ReadinessContributor readinessContributorA = mock(ReadinessContributor.class);
  private ReadinessContributor readinessContributorB = mock(ReadinessContributor.class);

  @Test
  public void bothReady() {
    when(readinessContributorA.isReady()).thenReturn(Boolean.TRUE);
    when(readinessContributorB.isReady()).thenReturn(Boolean.TRUE);
    assertEquals(Boolean.TRUE, readinessService.isReady());
  }

  @Test
  public void bothUnready() {
    when(readinessContributorA.isReady()).thenReturn(Boolean.FALSE);
    when(readinessContributorB.isReady()).thenReturn(Boolean.TRUE);
    assertEquals(Boolean.FALSE, readinessService.isReady());
  }

  @Test
  public void exists() {
    assertNotNull(readinessService);
    assertNotNull(readinessContributorA);
    assertNotNull(readinessContributorB);
  }

  @BeforeEach
  public void setup() {
    reset(readinessContributorA);
    reset(readinessContributorB);
    readinessService.registerReadinessContributor(readinessContributorA);
    readinessService.registerReadinessContributor(readinessContributorB);
  }

  @AfterEach
  public void tearDown() {
    readinessService.unregisterReadinessContributor(readinessContributorA);
    readinessService.unregisterReadinessContributor(readinessContributorB);
  }

  @Test
  public void unreadyA() {
    when(readinessContributorA.isReady()).thenReturn(Boolean.FALSE);
    when(readinessContributorB.isReady()).thenReturn(Boolean.TRUE);
    assertEquals(Boolean.FALSE, readinessService.isReady());
  }

  @Test
  public void unreadyB() {
    when(readinessContributorA.isReady()).thenReturn(Boolean.TRUE);
    when(readinessContributorB.isReady()).thenReturn(Boolean.FALSE);
    assertEquals(Boolean.FALSE, readinessService.isReady());
  }

  @Test
  public void unreadyException() {
    when(readinessContributorA.isReady()).thenReturn(Boolean.TRUE);
    when(readinessContributorB.isReady())
        .thenThrow(new RuntimeException(UUID.randomUUID().toString()));
    assertEquals(Boolean.FALSE, readinessService.isReady());
  }
}
