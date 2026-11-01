package br.com.rafaelpvs.telemetry_service.controllers.telemetry.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TelemetryResponse(
    UUID eventId,
    UUID deviceId,
    BigDecimal temperature,
    BigDecimal energyConsumption,
    Instant timestamp) {
}
