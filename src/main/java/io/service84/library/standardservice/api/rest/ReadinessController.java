package io.service84.library.standardservice.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.service84.library.standardservice.services.ReadinessService;

@RestController("B79499A4-F616-41A6-98A4-C2D7EBF28A54")
public class ReadinessController {
  @Autowired private ReadinessService service;

  @GetMapping(
      value = "/ready",
      produces = {"application/json"})
  public ResponseEntity<Void> isReady() {
    if (service.isReady()) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }
  }
}
