package br.com.rafaelpvs.telemetry_service.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTelemetryPeriodException extends RuntimeException {

  public InvalidTelemetryPeriodException() {
    super("start must be before or equal to end");
  }
}
