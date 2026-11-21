package br.com.rafaelpvs.alert_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rafaelpvs.alert_service.clients.device.DeviceClient;
import br.com.rafaelpvs.alert_service.clients.device.dtos.DeviceResponse;
import br.com.rafaelpvs.alert_service.consumers.telemetry.events.TelemetryReceivedEvent;
import br.com.rafaelpvs.alert_service.entities.AlertEntity;
import br.com.rafaelpvs.alert_service.entities.AlertStatus;
import br.com.rafaelpvs.alert_service.entities.AlertType;
import br.com.rafaelpvs.alert_service.repositories.AlertRepository;
import br.com.rafaelpvs.alert_service.services.exceptions.AlertNotFoundException;

@ExtendWith(MockitoExtension.class)
class AlertServiceTests {

  @Mock
  private AlertRepository alertRepository;

  @Mock
  private DeviceClient deviceClient;

  @InjectMocks
  private AlertService alertService;

  @Test
  void shouldCreateHighTemperatureAlert() {
    var event = event(new BigDecimal("95.5000"));
    when(deviceClient.findById(event.deviceId())).thenReturn(new DeviceResponse(
        event.deviceId(), true, new BigDecimal("20.0000"), new BigDecimal("80.0000")));
    when(alertRepository.existsByEventIdAndType(event.eventId(), AlertType.HIGH_TEMPERATURE))
        .thenReturn(false);

    alertService.process(event);

    var captor = ArgumentCaptor.forClass(AlertEntity.class);
    verify(alertRepository).save(captor.capture());
    var alert = captor.getValue();
    assertThat(alert.getEventId()).isEqualTo(event.eventId());
    assertThat(alert.getDeviceId()).isEqualTo(event.deviceId());
    assertThat(alert.getType()).isEqualTo(AlertType.HIGH_TEMPERATURE);
    assertThat(alert.getRecordedValue()).isEqualByComparingTo("95.5000");
    assertThat(alert.getConfiguredLimit()).isEqualByComparingTo("80.0000");
    assertThat(alert.getEventTimestamp()).isEqualTo(event.timestamp());
    assertThat(alert.getStatus()).isEqualTo(AlertStatus.OPEN);
  }

  @Test
  void shouldCreateLowTemperatureAlert() {
    var event = event(new BigDecimal("10.0000"));
    when(deviceClient.findById(event.deviceId())).thenReturn(new DeviceResponse(
        event.deviceId(), true, new BigDecimal("20.0000"), new BigDecimal("80.0000")));

    alertService.process(event);

    var captor = ArgumentCaptor.forClass(AlertEntity.class);
    verify(alertRepository).save(captor.capture());
    assertThat(captor.getValue().getType()).isEqualTo(AlertType.LOW_TEMPERATURE);
    assertThat(captor.getValue().getConfiguredLimit()).isEqualByComparingTo("20.0000");
  }

  @Test
  void shouldNotCreateAlertWithinConfiguredLimits() {
    var event = event(new BigDecimal("50.0000"));
    when(deviceClient.findById(event.deviceId())).thenReturn(new DeviceResponse(
        event.deviceId(), true, new BigDecimal("20.0000"), new BigDecimal("80.0000")));

    alertService.process(event);

    verify(alertRepository, never()).save(org.mockito.ArgumentMatchers.any());
  }

  @Test
  void shouldIgnoreDuplicateAlert() {
    var event = event(new BigDecimal("95.5000"));
    when(deviceClient.findById(event.deviceId())).thenReturn(new DeviceResponse(
        event.deviceId(), true, new BigDecimal("20.0000"), new BigDecimal("80.0000")));
    when(alertRepository.existsByEventIdAndType(event.eventId(), AlertType.HIGH_TEMPERATURE))
        .thenReturn(true);

    alertService.process(event);

    verify(alertRepository, never()).save(org.mockito.ArgumentMatchers.any());
  }

  @Test
  void shouldFindAlertsByDeviceAndStatus() {
    var deviceId = UUID.randomUUID();
    var expected = List.of(alert(deviceId));
    when(alertRepository.findByDeviceIdAndStatusOrderByCreatedAtDescIdDesc(
        deviceId, AlertStatus.OPEN)).thenReturn(expected);

    var result = alertService.findAll(deviceId, AlertStatus.OPEN);

    assertThat(result).isSameAs(expected);
  }

  @Test
  void shouldFindAlertsWithoutFilters() {
    var expected = List.of(alert(UUID.randomUUID()));
    when(alertRepository.findAllByOrderByCreatedAtDescIdDesc()).thenReturn(expected);

    var result = alertService.findAll(null, null);

    assertThat(result).isSameAs(expected);
  }

  @Test
  void shouldFindAlertById() {
    var id = UUID.randomUUID();
    var expected = alert(UUID.randomUUID());
    when(alertRepository.findById(id)).thenReturn(Optional.of(expected));

    assertThat(alertService.findById(id)).isSameAs(expected);
  }

  @Test
  void shouldRejectUnknownAlert() {
    var id = UUID.randomUUID();
    when(alertRepository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> alertService.findById(id))
        .isInstanceOf(AlertNotFoundException.class)
        .hasMessage("Alert not found: " + id);
  }

  @Test
  void shouldResolveOpenAlert() {
    var id = UUID.randomUUID();
    var alert = alert(UUID.randomUUID());
    when(alertRepository.findById(id)).thenReturn(Optional.of(alert));
    when(alertRepository.save(alert)).thenReturn(alert);

    var result = alertService.resolve(id);

    assertThat(result.getStatus()).isEqualTo(AlertStatus.RESOLVED);
    assertThat(result.getResolvedAt()).isNotNull();
    verify(alertRepository).save(alert);
  }

  @Test
  void shouldKeepOriginalResolutionTimestampWhenResolvingAgain() {
    var id = UUID.randomUUID();
    var alert = alert(UUID.randomUUID());
    var originalResolvedAt = Instant.parse("2026-11-20T12:00:00Z");
    alert.resolve(originalResolvedAt);
    when(alertRepository.findById(id)).thenReturn(Optional.of(alert));
    when(alertRepository.save(alert)).thenReturn(alert);

    var result = alertService.resolve(id);

    assertThat(result.getResolvedAt()).isEqualTo(originalResolvedAt);
  }

  private AlertEntity alert(UUID deviceId) {
    return new AlertEntity(
        UUID.randomUUID(),
        deviceId,
        AlertType.HIGH_TEMPERATURE,
        new BigDecimal("95.5000"),
        new BigDecimal("80.0000"),
        Instant.parse("2026-11-20T11:59:00Z"),
        Instant.parse("2026-11-20T12:00:00Z"));
  }

  private TelemetryReceivedEvent event(BigDecimal temperature) {
    return new TelemetryReceivedEvent(
        UUID.randomUUID(),
        UUID.randomUUID(),
        temperature,
        new BigDecimal("120.8000"),
        Instant.parse("2026-11-20T11:59:00Z"));
  }
}
