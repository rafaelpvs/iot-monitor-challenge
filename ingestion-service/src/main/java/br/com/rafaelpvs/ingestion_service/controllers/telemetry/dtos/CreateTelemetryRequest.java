package br.com.rafaelpvs.ingestion_service.controllers.telemetry.dtos;

import java.math.BigDecimal;
import java.time.Instant;

public record CreateTelemetryRequest(
    String deviceId,
    BigDecimal temperature,
    BigDecimal energyConsumption,
    Instant timestamp) {
}