package br.com.rafaelpvs.ingestion_service.services.telemetry.commands;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CreateTelemetryCommand(
    UUID eventId,
    UUID deviceId,
    BigDecimal temperature,
    BigDecimal energyConsumption,
    Instant timestamp) {
}
