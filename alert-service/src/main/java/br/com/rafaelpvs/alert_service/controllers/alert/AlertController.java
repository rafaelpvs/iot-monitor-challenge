package br.com.rafaelpvs.alert_service.controllers.alert;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rafaelpvs.alert_service.controllers.alert.dtos.AlertResponse;
import br.com.rafaelpvs.alert_service.controllers.alert.mappers.AlertMapper;
import br.com.rafaelpvs.alert_service.entities.AlertStatus;
import br.com.rafaelpvs.alert_service.services.AlertService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {

  private final AlertService alertService;
  private final AlertMapper alertMapper;

  @GetMapping
  public ResponseEntity<List<AlertResponse>> findAll(
      @RequestParam(required = false) UUID deviceId,
      @RequestParam(required = false) AlertStatus status) {
    var response = alertService.findAll(deviceId, status).stream()
        .map(alertMapper::toResponse)
        .toList();

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AlertResponse> findById(@PathVariable UUID id) {
    return ResponseEntity.ok(alertMapper.toResponse(alertService.findById(id)));
  }

  @PatchMapping("/{id}/resolve")
  public ResponseEntity<AlertResponse> resolve(@PathVariable UUID id) {
    return ResponseEntity.ok(alertMapper.toResponse(alertService.resolve(id)));
  }
}
