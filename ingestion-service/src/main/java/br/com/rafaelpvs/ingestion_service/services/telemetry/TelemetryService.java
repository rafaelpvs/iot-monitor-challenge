package br.com.rafaelpvs.ingestion_service.services.telemetry;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Service;

import br.com.rafaelpvs.ingestion_service.producers.telemetry.TelemetryProducer;
import br.com.rafaelpvs.ingestion_service.services.telemetry.commands.CreateTelemetryCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TelemetryService {

  private final TelemetryProducer telemetryProducer;

  public CompletableFuture<Void> create(CreateTelemetryCommand command) {
    return telemetryProducer.send(command)
        .thenAccept(result -> log.info(
            "Telemetry published: deviceId={}, partition={}, offset={}",
            command.deviceId(),
            result.getRecordMetadata().partition(),
            result.getRecordMetadata().offset()));
  }
}
