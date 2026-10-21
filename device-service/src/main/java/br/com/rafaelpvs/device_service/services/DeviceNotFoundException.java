package br.com.rafaelpvs.device_service.services;

import java.util.UUID;

public class DeviceNotFoundException extends RuntimeException {

  public DeviceNotFoundException(UUID id) {
    super("Device not found: " + id);
  }
}
