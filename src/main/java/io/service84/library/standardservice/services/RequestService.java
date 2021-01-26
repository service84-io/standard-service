package io.service84.library.standardservice.services;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;

@Service("2B8A15AC-BE5B-47D8-87CA-5B3034C73A13")
public class RequestService {
  private @Autowired HttpServletRequest request;

  public String getMethod() {
    if (request != null) {
      return request.getMethod();
    }

    return null;
  }

  public Optional<NativeWebRequest> getRequest() {
    if (request != null) {
      return Optional.of(new ServletWebRequest(request));
    }

    return Optional.empty();
  }

  public String getURL() {
    if (request != null) {
      return request.getRequestURI();
    }

    return null;
  }
}
