package br.com.rafaelpvs.ingestion_service.clients.device.dtos;

import java.util.UUID;

public record DeviceResponse(UUID id, boolean active) {
}
