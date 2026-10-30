package br.com.rafaelpvs.ingestion_service.services.telemetry;

import org.springframework.stereotype.Service;

import br.com.rafaelpvs.ingestion_service.services.telemetry.commands.CreateTelemetryCommand;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TelemetryService {

  public void create(CreateTelemetryCommand command) {
    log.info("Command: " + command.toString());
  }
}
