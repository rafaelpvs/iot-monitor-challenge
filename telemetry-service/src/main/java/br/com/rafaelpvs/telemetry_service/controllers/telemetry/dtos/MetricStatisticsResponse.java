package br.com.rafaelpvs.telemetry_service.controllers.telemetry.dtos;

import java.math.BigDecimal;

public record MetricStatisticsResponse(
    BigDecimal average,
    BigDecimal minimum,
    BigDecimal maximum) {
}
