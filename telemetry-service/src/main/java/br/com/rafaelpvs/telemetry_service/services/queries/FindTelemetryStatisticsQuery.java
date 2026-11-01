package br.com.rafaelpvs.telemetry_service.services.queries;

import java.time.Instant;
import java.util.UUID;

public record FindTelemetryStatisticsQuery(
    UUID deviceId,
    Instant start,
    Instant end) {
}
