package io.service84.library.standardservice.api.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.service84.library.standardservice.services.HealthService;

@RestController("F54F9031-013A-4447-A62B-A3EFDB56239B")
public class HealthController {
  @Autowired private HealthService service;

  @GetMapping(
      value = "/health",
      produces = {"application/json"})
  public ResponseEntity<Void> isHealthy() {
    if (service.isHealthy()) {
      return new ResponseEntity<>(HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
