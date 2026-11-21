package br.com.rafaelpvs.alert_service.controllers.alert.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import br.com.rafaelpvs.alert_service.entities.AlertStatus;
import br.com.rafaelpvs.alert_service.entities.AlertType;

public record AlertResponse(
    UUID id,
    UUID eventId,
    UUID deviceId,
    AlertType type,
    BigDecimal recordedValue,
    BigDecimal configuredLimit,
    AlertStatus status,
    Instant timestamp,
    Instant createdAt,
    Instant resolvedAt) {
}
