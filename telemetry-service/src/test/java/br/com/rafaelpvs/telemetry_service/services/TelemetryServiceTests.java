package br.com.rafaelpvs.telemetry_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rafaelpvs.telemetry_service.consumers.telemetry.events.TelemetryReceivedEvent;
import br.com.rafaelpvs.telemetry_service.entities.TelemetryEntity;
import br.com.rafaelpvs.telemetry_service.repositories.TelemetryRepository;
import br.com.rafaelpvs.telemetry_service.services.mappers.TelemetryMapper;

@ExtendWith(MockitoExtension.class)
class TelemetryServiceTests {

  @Mock
  private TelemetryRepository telemetryRepository;

  @Mock
  private TelemetryMapper telemetryMapper;

  @InjectMocks
  private TelemetryService telemetryService;

  @Test
  void shouldPersistNewTelemetry() {
    var event = event();
    var receivedAt = Instant.now();
    var entity = new TelemetryEntity(
        event.eventId(),
        event.deviceId(),
        event.temperature(),
        event.energyConsumption(),
        event.timestamp(),
        receivedAt);
    when(telemetryRepository.existsById(event.eventId())).thenReturn(false);
    when(telemetryMapper.toEntity(
        org.mockito.ArgumentMatchers.eq(event),
        org.mockito.ArgumentMatchers.any(Instant.class)))
        .thenReturn(entity);

    telemetryService.create(event);

    var captor = ArgumentCaptor.forClass(TelemetryEntity.class);
    verify(telemetryRepository).save(captor.capture());

    var telemetry = captor.getValue();
    assertThat(telemetry.getEventId()).isEqualTo(event.eventId());
    assertThat(telemetry.getDeviceId()).isEqualTo(event.deviceId());
    assertThat(telemetry.getTemperature()).isEqualByComparingTo(event.temperature());
    assertThat(telemetry.getEnergyConsumption()).isEqualByComparingTo(event.energyConsumption());
    assertThat(telemetry.getEventTimestamp()).isEqualTo(event.timestamp());
    assertThat(telemetry.getReceivedAt()).isEqualTo(receivedAt);
  }

  @Test
  void shouldIgnoreDuplicateTelemetry() {
    var event = event();
    when(telemetryRepository.existsById(event.eventId())).thenReturn(true);

    telemetryService.create(event);

    verify(telemetryRepository, never()).save(org.mockito.ArgumentMatchers.any());
  }

  private TelemetryReceivedEvent event() {
    return new TelemetryReceivedEvent(
        UUID.randomUUID(),
        UUID.randomUUID(),
        BigDecimal.valueOf(95.5),
        BigDecimal.valueOf(120.8),
        Instant.parse("2026-10-31T20:00:00Z"));
  }
}
