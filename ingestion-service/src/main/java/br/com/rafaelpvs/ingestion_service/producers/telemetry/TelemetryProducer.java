package br.com.rafaelpvs.ingestion_service.producers.telemetry;

import java.util.concurrent.CompletableFuture;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import br.com.rafaelpvs.ingestion_service.config.properties.TelemetryReceivedTopicProperties;
import br.com.rafaelpvs.ingestion_service.services.telemetry.commands.CreateTelemetryCommand;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TelemetryProducer {

  private final KafkaTemplate<String, Object> kafkaTemplate;
  private final TelemetryReceivedTopicProperties topicProperties;

  public CompletableFuture<SendResult<String, Object>> send(CreateTelemetryCommand command) {
    return kafkaTemplate.send(
        topicProperties.name(),
        command.deviceId(),
        command);
  }
}
