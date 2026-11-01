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
import br.com.rafaelpvs.telemetry_service.services.models.MetricStatistics;
import br.com.rafaelpvs.telemetry_service.services.models.TelemetryStatistics;
import br.com.rafaelpvs.telemetry_service.services.queries.FindTelemetryHistoryQuery;
import br.com.rafaelpvs.telemetry_service.services.queries.FindTelemetryStatisticsQuery;
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

  @Transactional(readOnly = true)
  public TelemetryStatistics findStatistics(FindTelemetryStatisticsQuery query) {
    validatePeriod(query.start(), query.end());
    var statistics = telemetryRepository.findStatistics(
        query.deviceId(), query.start(), query.end());

    return new TelemetryStatistics(
        new MetricStatistics(
            statistics.getAverageTemperature(),
            statistics.getMinimumTemperature(),
            statistics.getMaximumTemperature()),
        new MetricStatistics(
            statistics.getAverageEnergyConsumption(),
            statistics.getMinimumEnergyConsumption(),
            statistics.getMaximumEnergyConsumption()));
  }

  private void validatePeriod(FindTelemetryHistoryQuery query) {
    validatePeriod(query.start(), query.end());
  }

  private void validatePeriod(Instant start, Instant end) {
    if (start.isAfter(end)) {
      throw new InvalidTelemetryPeriodException();
    }
  }

  private PageRequest createPageable(FindTelemetryHistoryQuery query) {
    return PageRequest.of(query.page(), query.size());
  }
}
