package br.com.rafaelpvs.device_service.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import br.com.rafaelpvs.device_service.entities.DeviceEntity;

public interface DeviceRepository extends ListCrudRepository<DeviceEntity, UUID> {
}
