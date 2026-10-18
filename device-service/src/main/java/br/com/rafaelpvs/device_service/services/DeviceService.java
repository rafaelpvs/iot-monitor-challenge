package br.com.rafaelpvs.device_service.services;

import org.springframework.stereotype.Service;

import br.com.rafaelpvs.device_service.entities.DeviceEntity;
import br.com.rafaelpvs.device_service.repositories.DeviceRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeviceService {
  private final DeviceRepository deviceRepository;

  public DeviceEntity create(DeviceEntity entity) {
    this.deviceRepository.save(entity);
    return entity;
  }
}
