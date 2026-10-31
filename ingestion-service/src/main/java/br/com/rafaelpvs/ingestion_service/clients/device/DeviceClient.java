package br.com.rafaelpvs.ingestion_service.clients.device;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import br.com.rafaelpvs.ingestion_service.clients.device.dtos.DeviceResponse;
import br.com.rafaelpvs.ingestion_service.services.telemetry.exceptions.DeviceNotFoundException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeviceClient {

  private final RestClient restClient;

  public DeviceResponse findById(UUID id) {
    try {
      return restClient.get()
          .uri("/devices/{id}", id)
          .retrieve()
          .body(DeviceResponse.class);
    } catch (HttpClientErrorException.NotFound exception) {
      throw new DeviceNotFoundException(id, exception);
    }
  }
}
