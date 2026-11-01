package br.com.rafaelpvs.telemetry_service.consumers.telemetry.events;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TelemetryReceivedEvent(
    UUID eventId,
    UUID deviceId,
    BigDecimal temperature,
    BigDecimal energyConsumption,
    Instant timestamp) {
}
