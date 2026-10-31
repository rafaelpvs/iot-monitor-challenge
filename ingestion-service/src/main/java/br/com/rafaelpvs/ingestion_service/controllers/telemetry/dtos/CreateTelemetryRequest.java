package br.com.rafaelpvs.ingestion_service.controllers.telemetry.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateTelemetryRequest(
        @NotNull UUID eventId,

        @NotNull UUID deviceId,

        @NotNull @DecimalMin("-100.0") @DecimalMax("1000.0") BigDecimal temperature,

        @NotNull @PositiveOrZero BigDecimal energyConsumption,

        @NotNull Instant timestamp) {
}
