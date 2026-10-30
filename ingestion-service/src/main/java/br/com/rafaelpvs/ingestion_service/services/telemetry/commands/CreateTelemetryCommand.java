package br.com.rafaelpvs.ingestion_service.services.telemetry.commands;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateTelemetryCommand(
    String deviceId,
    BigDecimal temperature,
    BigDecimal energyConsumption,
    Instant timestamp) {
}
