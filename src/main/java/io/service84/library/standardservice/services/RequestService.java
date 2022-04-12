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

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

@Service("2B8A15AC-BE5B-47D8-87CA-5B3034C73A13")
public class RequestService {
  private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

  private @Autowired HttpServletRequest request;

  public String getMethod() {
    logger.debug("getMethod");
    if (request != null) {
      return request.getMethod();
    }

    return null;
  }

  public Optional<NativeWebRequest> getRequest() {
    logger.debug("getRequest");
    if (request != null) {
      return Optional.of(new ServletWebRequest(request));
    }

    return Optional.empty();
  }

  public String getURL() {
    logger.debug("getURL");
    if (request != null) {
      return request.getRequestURI();
    }

    return null;
  }

  public String getPath() {
    logger.debug("getPath");
    if (request != null) {
      StringBuilder sb = new StringBuilder();
      sb.append(denull(request.getContextPath()));
      sb.append(denull(request.getServletPath()));
      sb.append(denull(request.getPathInfo()));
      return sb.toString();
    }

    return null;
  }

  private String denull(String value) {
    if (value != null) {
      return value;
    }

    return "";
  }
}
