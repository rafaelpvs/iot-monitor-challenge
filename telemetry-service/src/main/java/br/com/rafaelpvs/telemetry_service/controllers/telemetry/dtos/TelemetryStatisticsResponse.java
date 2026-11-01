package br.com.rafaelpvs.telemetry_service.controllers.telemetry.dtos;

import br.com.rafaelpvs.telemetry_service.services.models.TelemetryStatistics;

public record TelemetryStatisticsResponse(
    MetricStatisticsResponse temperature,
    MetricStatisticsResponse energyConsumption) {

  public static TelemetryStatisticsResponse from(TelemetryStatistics statistics) {
    return new TelemetryStatisticsResponse(
        new MetricStatisticsResponse(
            statistics.temperature().average(),
            statistics.temperature().minimum(),
            statistics.temperature().maximum()),
        new MetricStatisticsResponse(
            statistics.energyConsumption().average(),
            statistics.energyConsumption().minimum(),
            statistics.energyConsumption().maximum()));
  }
}
