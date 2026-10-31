package br.com.rafaelpvs.ingestion_service.services.telemetry.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_CONTENT)
public class InactiveDeviceException extends RuntimeException {

  public InactiveDeviceException(UUID deviceId) {
    super("Device is inactive: " + deviceId);
  }
}
