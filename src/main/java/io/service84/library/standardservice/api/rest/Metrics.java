package io.service84.library.standardservice.api.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.service84.library.standardservice.services.MetricsService;

@RestController("522DE21D-310B-4C4F-BAD9-7E764A540CFB")
public class Metrics {
  @Autowired private MetricsService service;

  @GetMapping(
      value = "/metrics",
      produces = {"application/json"})
  @ResponseStatus(HttpStatus.OK)
  public Map<String, Object> getMetrics() {
    return service.getMetrics();
  }
}
