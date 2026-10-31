package br.com.rafaelpvs.ingestion_service.controllers.telemetry.dtos;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;

class CreateTelemetryRequestValidationTests {

  @Test
  void shouldAcceptValidRequest() {
    try (var factory = Validation.buildDefaultValidatorFactory()) {
      var validator = factory.getValidator();
      var request = new CreateTelemetryRequest(
          UUID.randomUUID(),
          UUID.randomUUID(),
          BigDecimal.valueOf(95.5),
          BigDecimal.valueOf(120.8),
          Instant.now());

      assertThat(validator.validate(request)).isEmpty();
    }
  }

  @Test
  void shouldRejectInvalidTelemetryFields() {
    try (var factory = Validation.buildDefaultValidatorFactory()) {
      var validator = factory.getValidator();
      var request = new CreateTelemetryRequest(
          null,
          null,
          BigDecimal.valueOf(1000.1),
          BigDecimal.valueOf(-0.1),
          null);

      assertThat(validator.validate(request))
          .extracting(violation -> violation.getPropertyPath().toString())
          .containsExactlyInAnyOrder(
              "eventId",
              "deviceId",
              "temperature",
              "energyConsumption",
              "timestamp");
    }
  }
}
