package br.com.rafaelpvs.alert_service.clients.device;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import br.com.rafaelpvs.alert_service.clients.device.dtos.DeviceResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeviceClient {

  private final RestClient deviceRestClient;

  public DeviceResponse findById(UUID id) {
    return deviceRestClient.get()
        .uri("/devices/{id}", id)
        .retrieve()
        .body(DeviceResponse.class);
  }
}
