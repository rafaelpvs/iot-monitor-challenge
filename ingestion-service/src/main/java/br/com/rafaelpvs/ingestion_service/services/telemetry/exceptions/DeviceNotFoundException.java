package br.com.rafaelpvs.ingestion_service.services.telemetry.exceptions;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DeviceNotFoundException extends RuntimeException {

  public DeviceNotFoundException(UUID deviceId, Throwable cause) {
    super("Device not found: " + deviceId, cause);
  }
}
