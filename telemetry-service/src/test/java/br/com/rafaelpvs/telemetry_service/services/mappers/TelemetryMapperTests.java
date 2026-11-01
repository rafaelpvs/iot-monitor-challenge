package br.com.rafaelpvs.telemetry_service.services.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import br.com.rafaelpvs.telemetry_service.consumers.telemetry.events.TelemetryReceivedEvent;

class TelemetryMapperTests {

  private final TelemetryMapper telemetryMapper = Mappers.getMapper(TelemetryMapper.class);

  @Test
  void shouldMapEventToEntity() {
    var event = new TelemetryReceivedEvent(
        UUID.randomUUID(),
        UUID.randomUUID(),
        BigDecimal.valueOf(95.5),
        BigDecimal.valueOf(120.8),
        Instant.parse("2026-10-31T20:00:00Z"));
    var receivedAt = Instant.parse("2026-10-31T20:00:01Z");

    var entity = telemetryMapper.toEntity(event, receivedAt);

    assertThat(entity.getEventId()).isEqualTo(event.eventId());
    assertThat(entity.getDeviceId()).isEqualTo(event.deviceId());
    assertThat(entity.getTemperature()).isEqualByComparingTo(event.temperature());
    assertThat(entity.getEnergyConsumption()).isEqualByComparingTo(event.energyConsumption());
    assertThat(entity.getEventTimestamp()).isEqualTo(event.timestamp());
    assertThat(entity.getReceivedAt()).isEqualTo(receivedAt);
  }
}
