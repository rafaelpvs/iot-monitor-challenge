package br.com.rafaelpvs.telemetry_service.services.models;

import java.math.BigDecimal;

public record MetricStatistics(
    BigDecimal average,
    BigDecimal minimum,
    BigDecimal maximum) {
}
