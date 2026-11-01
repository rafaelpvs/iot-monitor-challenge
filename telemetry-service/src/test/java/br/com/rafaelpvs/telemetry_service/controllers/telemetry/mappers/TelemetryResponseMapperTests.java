package br.com.rafaelpvs.telemetry_service.controllers.telemetry.mappers;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import br.com.rafaelpvs.telemetry_service.entities.TelemetryEntity;

class TelemetryResponseMapperTests {

  private final TelemetryResponseMapper mapper = Mappers.getMapper(TelemetryResponseMapper.class);

  @Test
  void shouldMapEntityToResponse() {
    var entity = new TelemetryEntity(
        UUID.randomUUID(),
        UUID.randomUUID(),
        BigDecimal.valueOf(95.5),
        BigDecimal.valueOf(120.8),
        Instant.parse("2026-11-01T10:00:00Z"),
        Instant.parse("2026-11-01T10:00:01Z"));

    var response = mapper.toResponse(entity);

    assertThat(response.eventId()).isEqualTo(entity.getEventId());
    assertThat(response.deviceId()).isEqualTo(entity.getDeviceId());
    assertThat(response.temperature()).isEqualByComparingTo(entity.getTemperature());
    assertThat(response.energyConsumption()).isEqualByComparingTo(entity.getEnergyConsumption());
    assertThat(response.timestamp()).isEqualTo(entity.getEventTimestamp());
  }
}
