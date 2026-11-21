package br.com.rafaelpvs.alert_service.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.rafaelpvs.alert_service.clients.device.DeviceClient;
import br.com.rafaelpvs.alert_service.consumers.telemetry.events.TelemetryReceivedEvent;
import br.com.rafaelpvs.alert_service.entities.AlertEntity;
import br.com.rafaelpvs.alert_service.entities.AlertStatus;
import br.com.rafaelpvs.alert_service.entities.AlertType;
import br.com.rafaelpvs.alert_service.repositories.AlertRepository;
import br.com.rafaelpvs.alert_service.services.exceptions.AlertNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlertService {

  private final AlertRepository alertRepository;
  private final DeviceClient deviceClient;

  @Transactional
  public void process(TelemetryReceivedEvent event) {
    var device = deviceClient.findById(event.deviceId());

    if (device.minimumLimit() != null
        && event.temperature().compareTo(device.minimumLimit()) < 0) {
      createAlert(event, AlertType.LOW_TEMPERATURE, device.minimumLimit());
    }

    if (device.maximumLimit() != null
        && event.temperature().compareTo(device.maximumLimit()) > 0) {
      createAlert(event, AlertType.HIGH_TEMPERATURE, device.maximumLimit());
    }
  }

  @Transactional(readOnly = true)
  public List<AlertEntity> findAll(UUID deviceId, AlertStatus status) {
    if (deviceId != null && status != null) {
      return alertRepository.findByDeviceIdAndStatusOrderByCreatedAtDescIdDesc(deviceId, status);
    }
    if (deviceId != null) {
      return alertRepository.findByDeviceIdOrderByCreatedAtDescIdDesc(deviceId);
    }
    if (status != null) {
      return alertRepository.findByStatusOrderByCreatedAtDescIdDesc(status);
    }
    return alertRepository.findAllByOrderByCreatedAtDescIdDesc();
  }

  @Transactional(readOnly = true)
  public AlertEntity findById(UUID id) {
    return alertRepository.findById(id)
        .orElseThrow(() -> new AlertNotFoundException(id));
  }

  @Transactional
  public AlertEntity resolve(UUID id) {
    var alert = findById(id);
    alert.resolve(Instant.now());
    return alertRepository.save(alert);
  }

  private void createAlert(
      TelemetryReceivedEvent event,
      AlertType type,
      BigDecimal configuredLimit) {
    if (alertRepository.existsByEventIdAndType(event.eventId(), type)) {
      return;
    }

    alertRepository.save(new AlertEntity(
        event.eventId(),
        event.deviceId(),
        type,
        event.temperature(),
        configuredLimit,
        event.timestamp(),
        Instant.now()));
  }
}
