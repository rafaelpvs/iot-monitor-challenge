package br.com.rafaelpvs.device_service.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.rafaelpvs.device_service.entities.DeviceEntity;

public interface DeviceRepository extends CrudRepository<DeviceEntity, UUID> {
}
