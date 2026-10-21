package br.com.rafaelpvs.device_service.controllers.device;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaelpvs.device_service.controllers.device.dtos.CreateDeviceRequest;
import br.com.rafaelpvs.device_service.controllers.device.dtos.CreateDeviceResponse;
import br.com.rafaelpvs.device_service.controllers.device.dtos.DeviceResponse;
import br.com.rafaelpvs.device_service.controllers.device.dtos.UpdateDeviceRequest;
import br.com.rafaelpvs.device_service.controllers.device.dtos.UpdateDeviceStatusRequest;
import br.com.rafaelpvs.device_service.controllers.device.mappers.DeviceMapper;
import br.com.rafaelpvs.device_service.services.DeviceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

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

  @GetMapping
  public ResponseEntity<List<DeviceResponse>> findAll() {
    var response = this.deviceService.findAll().stream()
        .map(this.deviceMapper::toResponse)
        .toList();
    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DeviceResponse> findById(@PathVariable UUID id) {
    var response = this.deviceMapper.toResponse(this.deviceService.findById(id));
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<DeviceResponse> update(
      @PathVariable UUID id,
      @Valid @RequestBody UpdateDeviceRequest request) {
    var device = this.deviceService.findById(id);
    this.deviceMapper.updateEntity(request, device);
    var persistedDevice = this.deviceService.update(device);
    var response = this.deviceMapper.toResponse(persistedDevice);
    return ResponseEntity.ok(response);
  }

  @PatchMapping("/{id}/status")
  public ResponseEntity<DeviceResponse> updateStatus(
      @PathVariable UUID id,
      @Valid @RequestBody UpdateDeviceStatusRequest request) {
    var response = this.deviceMapper.toResponse(
        this.deviceService.updateStatus(id, request.active()));
    return ResponseEntity.ok(response);
  }

}
