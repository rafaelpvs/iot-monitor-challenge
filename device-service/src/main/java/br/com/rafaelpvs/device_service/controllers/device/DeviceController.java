package br.com.rafaelpvs.device_service.controllers.device;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaelpvs.device_service.controllers.device.dtos.CreateDeviceRequest;
import br.com.rafaelpvs.device_service.controllers.device.dtos.CreateDeviceResponse;
import br.com.rafaelpvs.device_service.controllers.device.mappers.DeviceMapper;
import br.com.rafaelpvs.device_service.services.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("devices")
public class DeviceController {
  private final DeviceMapper deviceMapper;
  private final DeviceService deviceService;

  @PostMapping
  public ResponseEntity<CreateDeviceResponse> create(@Valid @RequestBody CreateDeviceRequest request) {
    var entity = this.deviceMapper.toEntity(request);
    var persistedEntity = this.deviceService.create(entity);
    var response = this.deviceMapper.toCreateResponse(persistedEntity);
    return ResponseEntity.ok(response);
  }

}
