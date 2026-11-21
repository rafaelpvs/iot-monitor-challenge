package br.com.rafaelpvs.alert_service.clients.device.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record DeviceResponse(
    UUID id,
    boolean active,
    BigDecimal minimumLimit,
    BigDecimal maximumLimit) {
}
