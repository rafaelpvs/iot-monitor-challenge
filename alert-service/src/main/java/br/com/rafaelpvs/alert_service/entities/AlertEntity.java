package br.com.rafaelpvs.alert_service.entities;

import static lombok.AccessLevel.PROTECTED;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "alerts")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class AlertEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(name = "event_id", nullable = false, updatable = false)
  private UUID eventId;

  @Column(name = "device_id", nullable = false, updatable = false)
  private UUID deviceId;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 50, updatable = false)
  private AlertType type;

  @Column(name = "recorded_value", nullable = false, precision = 19, scale = 4, updatable = false)
  private BigDecimal recordedValue;

  @Column(name = "configured_limit", nullable = false, precision = 19, scale = 4, updatable = false)
  private BigDecimal configuredLimit;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private AlertStatus status;

  @Column(name = "event_timestamp", nullable = false, updatable = false)
  private Instant eventTimestamp;

  @Column(name = "created_at", nullable = false, updatable = false)
  private Instant createdAt;

  @Column(name = "resolved_at")
  private Instant resolvedAt;

  public AlertEntity(
      UUID eventId,
      UUID deviceId,
      AlertType type,
      BigDecimal recordedValue,
      BigDecimal configuredLimit,
      Instant eventTimestamp,
      Instant createdAt) {
    this.eventId = eventId;
    this.deviceId = deviceId;
    this.type = type;
    this.recordedValue = recordedValue;
    this.configuredLimit = configuredLimit;
    this.status = AlertStatus.OPEN;
    this.eventTimestamp = eventTimestamp;
    this.createdAt = createdAt;
  }

  public void resolve(Instant resolvedAt) {
    if (status == AlertStatus.RESOLVED) {
      return;
    }

    status = AlertStatus.RESOLVED;
    this.resolvedAt = resolvedAt;
  }
}
