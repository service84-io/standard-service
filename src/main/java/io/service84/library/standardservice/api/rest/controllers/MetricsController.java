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

package io.service84.library.standardservice.api.rest.controllers;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.service84.library.standardservice.services.MetricsService;

@RestController("522DE21D-310B-4C4F-BAD9-7E764A540CFB")
public class MetricsController {
  private static final Logger logger = LoggerFactory.getLogger(MetricsController.class);

  @Autowired private MetricsService service;

  @GetMapping(
      value = "/metrics",
      produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Object> getMetrics() {
    logger.debug("getMetrics");
    return service.getMetrics();
  }
}
