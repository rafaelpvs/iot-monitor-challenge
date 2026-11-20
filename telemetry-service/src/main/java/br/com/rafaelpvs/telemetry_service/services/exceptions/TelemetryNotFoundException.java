package br.com.rafaelpvs.telemetry_service.services.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TelemetryNotFoundException extends RuntimeException {

  public TelemetryNotFoundException(UUID deviceId) {
    super("No telemetry found for device " + deviceId);
  }
}
