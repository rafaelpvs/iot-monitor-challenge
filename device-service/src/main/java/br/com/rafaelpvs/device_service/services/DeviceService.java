package br.com.rafaelpvs.device_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.com.rafaelpvs.device_service.entities.DeviceEntity;
import br.com.rafaelpvs.device_service.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceService {
  private final DeviceRepository deviceRepository;

  public DeviceEntity create(DeviceEntity entity) {
    return this.deviceRepository.save(entity);
  }

  public List<DeviceEntity> findAll() {
    return this.deviceRepository.findAll();
  }

  public DeviceEntity findById(UUID id) {
    return this.deviceRepository.findById(id)
        .orElseThrow(() -> new DeviceNotFoundException(id));
  }

  public DeviceEntity update(DeviceEntity device) {
    return this.deviceRepository.save(device);
  }

  public DeviceEntity updateStatus(UUID id, boolean active) {
    var device = findById(id);
    device.setActive(active);
    return this.deviceRepository.save(device);
  }
}
