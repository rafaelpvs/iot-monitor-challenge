package br.com.rafaelpvs.telemetry_service.controllers.telemetry;

import java.time.Instant;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaelpvs.telemetry_service.controllers.telemetry.dtos.PageResponse;
import br.com.rafaelpvs.telemetry_service.controllers.telemetry.dtos.TelemetryResponse;
import br.com.rafaelpvs.telemetry_service.controllers.telemetry.dtos.TelemetryStatisticsResponse;
import br.com.rafaelpvs.telemetry_service.controllers.telemetry.mappers.TelemetryResponseMapper;
import br.com.rafaelpvs.telemetry_service.services.TelemetryService;
import br.com.rafaelpvs.telemetry_service.services.queries.FindTelemetryHistoryQuery;
import br.com.rafaelpvs.telemetry_service.services.queries.FindTelemetryStatisticsQuery;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/telemetry/{deviceId}")
@RequiredArgsConstructor
@Validated
public class TelemetryController {

  private final TelemetryService telemetryService;
  private final TelemetryResponseMapper telemetryResponseMapper;

  @GetMapping
  public ResponseEntity<PageResponse<TelemetryResponse>> findHistory(
      @PathVariable UUID deviceId,
      @RequestParam Instant start,
      @RequestParam Instant end,
      @RequestParam(defaultValue = "0") @Min(0) int page,
      @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size) {
    var query = new FindTelemetryHistoryQuery(deviceId, start, end, page, size);
    var response = telemetryService.findHistory(query)
        .map(telemetryResponseMapper::toResponse);

    return ResponseEntity.ok(PageResponse.from(response));
  }

  @GetMapping("/statistics")
  public ResponseEntity<TelemetryStatisticsResponse> findStatistics(
      @PathVariable UUID deviceId,
      @RequestParam Instant start,
      @RequestParam Instant end) {
    var query = new FindTelemetryStatisticsQuery(deviceId, start, end);
    var response = TelemetryStatisticsResponse.from(telemetryService.findStatistics(query));

    return ResponseEntity.ok(response);
  }
}
