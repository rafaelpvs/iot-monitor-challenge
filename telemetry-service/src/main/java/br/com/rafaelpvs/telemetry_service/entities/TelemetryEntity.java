package br.com.rafaelpvs.telemetry_service.entities;

import static lombok.AccessLevel.PROTECTED;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "telemetry_readings")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class TelemetryEntity {

  @Id
  @Column(name = "event_id", nullable = false, updatable = false)
  private UUID eventId;

  @Column(name = "device_id", nullable = false, updatable = false)
  private UUID deviceId;

  @Column(nullable = false, precision = 19, scale = 4, updatable = false)
  private BigDecimal temperature;

  @Column(name = "energy_consumption", nullable = false, precision = 19, scale = 4, updatable = false)
  private BigDecimal energyConsumption;

  @Column(name = "event_timestamp", nullable = false, updatable = false)
  private Instant eventTimestamp;

  @Column(name = "received_at", nullable = false, updatable = false)
  private Instant receivedAt;

  public TelemetryEntity(
      UUID eventId,
      UUID deviceId,
      BigDecimal temperature,
      BigDecimal energyConsumption,
      Instant eventTimestamp,
      Instant receivedAt) {
    this.eventId = eventId;
    this.deviceId = deviceId;
    this.temperature = temperature;
    this.energyConsumption = energyConsumption;
    this.eventTimestamp = eventTimestamp;
    this.receivedAt = receivedAt;
  }
}
