package br.com.rafaelpvs.telemetry_service.services;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rafaelpvs.telemetry_service.consumers.telemetry.events.TelemetryReceivedEvent;
import br.com.rafaelpvs.telemetry_service.repositories.TelemetryRepository;
import br.com.rafaelpvs.telemetry_service.services.mappers.TelemetryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TelemetryService {

  private final TelemetryRepository telemetryRepository;
  private final TelemetryMapper telemetryMapper;

  @Transactional
  public void create(TelemetryReceivedEvent event) {
    if (telemetryRepository.existsById(event.eventId())) {
      log.info("Duplicate telemetry ignored: eventId={}", event.eventId());
      return;
    }

    var telemetry = telemetryMapper.toEntity(event, Instant.now());

    telemetryRepository.save(telemetry);
  }
}
