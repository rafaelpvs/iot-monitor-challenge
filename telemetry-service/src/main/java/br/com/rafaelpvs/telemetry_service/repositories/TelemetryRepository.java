package br.com.rafaelpvs.telemetry_service.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rafaelpvs.telemetry_service.entities.TelemetryEntity;

public interface TelemetryRepository extends JpaRepository<TelemetryEntity, UUID> {
}
