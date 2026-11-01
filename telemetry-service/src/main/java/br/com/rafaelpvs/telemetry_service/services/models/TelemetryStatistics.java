package br.com.rafaelpvs.telemetry_service.services.models;

public record TelemetryStatistics(
    MetricStatistics temperature,
    MetricStatistics energyConsumption) {
}
