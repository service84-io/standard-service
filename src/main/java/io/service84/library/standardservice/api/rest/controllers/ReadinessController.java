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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.service84.library.standardservice.services.ReadinessService;

@RestController("B79499A4-F616-41A6-98A4-C2D7EBF28A54")
public class ReadinessController {
  private static final Logger logger = LoggerFactory.getLogger(ReadinessController.class);

  @Autowired private ReadinessService service;

  @GetMapping(
      value = "/ready",
      produces = {"application/json"})
  public ResponseEntity<Void> isReady() {
    logger.debug("isReady");
    if (service.isReady()) {
      return new ResponseEntity<>(HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
  }
}
