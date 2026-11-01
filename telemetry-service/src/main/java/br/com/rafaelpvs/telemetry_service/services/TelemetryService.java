package br.com.rafaelpvs.telemetry_service.services;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rafaelpvs.telemetry_service.consumers.telemetry.events.TelemetryReceivedEvent;
import br.com.rafaelpvs.telemetry_service.entities.TelemetryEntity;
import br.com.rafaelpvs.telemetry_service.repositories.TelemetryRepository;
import br.com.rafaelpvs.telemetry_service.services.exceptions.InvalidTelemetryPeriodException;
import br.com.rafaelpvs.telemetry_service.services.mappers.TelemetryMapper;
import br.com.rafaelpvs.telemetry_service.services.queries.FindTelemetryHistoryQuery;
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

  @Transactional(readOnly = true)
  public Page<TelemetryEntity> findHistory(FindTelemetryHistoryQuery query) {
    validatePeriod(query);
    return telemetryRepository
        .findByDeviceIdAndEventTimestampBetweenOrderByEventTimestampDescEventIdDesc(
        query.deviceId(),
        query.start(),
        query.end(),
        createPageable(query));
  }

  private void validatePeriod(FindTelemetryHistoryQuery query) {
    if (query.start().isAfter(query.end())) {
      throw new InvalidTelemetryPeriodException();
    }
  }

  private PageRequest createPageable(FindTelemetryHistoryQuery query) {
    return PageRequest.of(query.page(), query.size());
  }
}
