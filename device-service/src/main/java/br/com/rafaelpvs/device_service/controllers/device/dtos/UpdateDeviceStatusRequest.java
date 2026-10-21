package br.com.rafaelpvs.device_service.controllers.device.dtos;

import jakarta.validation.constraints.NotNull;

public record UpdateDeviceStatusRequest(
    @NotNull Boolean active) {
}
