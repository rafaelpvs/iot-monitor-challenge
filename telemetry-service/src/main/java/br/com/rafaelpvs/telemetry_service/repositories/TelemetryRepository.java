package br.com.rafaelpvs.telemetry_service.repositories;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rafaelpvs.telemetry_service.entities.TelemetryEntity;

public interface TelemetryRepository extends JpaRepository<TelemetryEntity, UUID> {

  Page<TelemetryEntity> findByDeviceIdAndEventTimestampBetweenOrderByEventTimestampDescEventIdDesc(
      UUID deviceId,
      Instant start,
      Instant end,
      Pageable pageable);
}
