package br.com.rafaelpvs.ingestion_service.controllers.telemetry;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaelpvs.ingestion_service.controllers.telemetry.dtos.CreateTelemetryRequest;
import br.com.rafaelpvs.ingestion_service.controllers.telemetry.mappers.TelemetryMapper;
import br.com.rafaelpvs.ingestion_service.services.telemetry.TelemetryService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("telemetry")
public class TelemetryController {

  private final TelemetryMapper telemetryMapper;
  private final TelemetryService telemetryService;

  @PostMapping
  public CompletableFuture<ResponseEntity<Void>> create(@RequestBody CreateTelemetryRequest request) {
    var command = telemetryMapper.toCommand(request);
    return telemetryService.create(command)
        .thenApply(ignored -> ResponseEntity.accepted().build());
  }

}
