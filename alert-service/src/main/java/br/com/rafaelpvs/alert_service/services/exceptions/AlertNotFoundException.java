package br.com.rafaelpvs.alert_service.services.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlertNotFoundException extends RuntimeException {

  public AlertNotFoundException(UUID id) {
    super("Alert not found: " + id);
  }
}
