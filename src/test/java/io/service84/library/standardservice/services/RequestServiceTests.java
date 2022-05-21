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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.NativeWebRequest;

@ExtendWith(SpringExtension.class)
public class RequestServiceTests {
  @TestConfiguration
  public static class Configuration {
    @Bean
    public HttpServletRequest getHttpServletRequest() {
      return mock(HttpServletRequest.class);
    }

    @Bean
    public RequestService getRequestService() {
      return new RequestService();
    }
  }

  // Test Subject
  @Autowired private RequestService requestService;

  @Autowired private HttpServletRequest mockHttpServletRequest;

  @Test
  public void exists() {
    assertNotNull(requestService);
    assertNotNull(mockHttpServletRequest);
  }

  @Test
  public void getMethodGET() {
    when(mockHttpServletRequest.getMethod()).thenReturn("GET");
    String method = requestService.getMethod();
    assertEquals("GET", method);
  }

  @Test
  public void getMethodPATCH() {
    when(mockHttpServletRequest.getMethod()).thenReturn("PATCH");
    String method = requestService.getMethod();
    assertEquals("PATCH", method);
  }

  @Test
  public void getRequest() {
    Optional<NativeWebRequest> request = requestService.getRequest();
    assertTrue(request.isPresent());
  }

  @Test
  public void getURL() {
    when(mockHttpServletRequest.getRequestURI()).thenReturn("http://example.com/path");
    String url = requestService.getURL();
    assertEquals("http://example.com/path", url);
  }

  @Test
  public void getUsualPath() {
    when(mockHttpServletRequest.getContextPath()).thenReturn("");
    when(mockHttpServletRequest.getServletPath()).thenReturn("/path");
    when(mockHttpServletRequest.getPathInfo()).thenReturn(null);
    String path = requestService.getPath();
    assertEquals("/path", path);
  }

  @Test
  public void getWithContextPath() {
    when(mockHttpServletRequest.getContextPath()).thenReturn("/context");
    when(mockHttpServletRequest.getServletPath()).thenReturn("/path");
    when(mockHttpServletRequest.getPathInfo()).thenReturn(null);
    String path = requestService.getPath();
    assertEquals("/context/path", path);
  }

  @Test
  public void getWithContextWithInfoPath() {
    when(mockHttpServletRequest.getContextPath()).thenReturn("/context");
    when(mockHttpServletRequest.getServletPath()).thenReturn("/path");
    when(mockHttpServletRequest.getPathInfo()).thenReturn("/info");
    String path = requestService.getPath();
    assertEquals("/context/path/info", path);
  }

  @BeforeEach
  private void setup() {
    reset(mockHttpServletRequest);
  }
}
