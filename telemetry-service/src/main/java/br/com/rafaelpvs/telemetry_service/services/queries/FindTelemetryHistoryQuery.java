package br.com.rafaelpvs.telemetry_service.services.queries;

import java.time.Instant;
import java.util.UUID;

public record FindTelemetryHistoryQuery(
    UUID deviceId,
    Instant start,
    Instant end,
    int page,
    int size) {
}
