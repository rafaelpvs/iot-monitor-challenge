package br.com.rafaelpvs.telemetry_service.consumers.telemetry;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import br.com.rafaelpvs.telemetry_service.consumers.telemetry.events.TelemetryReceivedEvent;
import br.com.rafaelpvs.telemetry_service.services.TelemetryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class TelemetryReceivedConsumer {

  private final TelemetryService telemetryService;

  @KafkaListener(
      topics = "${app.kafka.topics.telemetry-received}",
      groupId = "${spring.kafka.consumer.group-id}")
  public void consume(TelemetryReceivedEvent event) {
    log.info("Telemetry received: {}", event);
    telemetryService.create(event);
  }
}
