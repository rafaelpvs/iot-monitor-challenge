package br.com.rafaelpvs.ingestion_service.services.telemetry;

import org.springframework.stereotype.Service;

import br.com.rafaelpvs.ingestion_service.services.telemetry.commands.CreateTelemetryCommand;

@Service
public class TelemetryService {

  public void create(CreateTelemetryCommand command) {
    // A validação do dispositivo e a publicação no Kafka entrarão aqui.
  }
}
