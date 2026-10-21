package br.com.rafaelpvs.device_service.controllers.device.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record DeviceResponse(
    UUID id,
    String name,
    String type,
    String location,
    boolean active,
    BigDecimal minimumLimit,
    BigDecimal maximumLimit,
    Instant createdAt,
    Instant updatedAt) {
}
