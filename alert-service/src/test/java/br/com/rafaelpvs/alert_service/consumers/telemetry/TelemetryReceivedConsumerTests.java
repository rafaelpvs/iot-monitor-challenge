package br.com.rafaelpvs.alert_service.consumers.telemetry;

import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rafaelpvs.alert_service.consumers.telemetry.events.TelemetryReceivedEvent;
import br.com.rafaelpvs.alert_service.services.AlertService;

@ExtendWith(MockitoExtension.class)
class TelemetryReceivedConsumerTests {

  @Mock
  private AlertService alertService;

  @InjectMocks
  private TelemetryReceivedConsumer consumer;

  @Test
  void shouldDelegateTelemetryForAlertEvaluation() {
    var event = new TelemetryReceivedEvent(
        UUID.randomUUID(),
        UUID.randomUUID(),
        new BigDecimal("95.5000"),
        new BigDecimal("120.8000"),
        Instant.parse("2026-11-20T11:59:00Z"));

    consumer.consume(event);

    verify(alertService).process(event);
  }
}
