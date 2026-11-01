package br.com.rafaelpvs.telemetry_service.repositories;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.rafaelpvs.telemetry_service.entities.TelemetryEntity;

public interface TelemetryRepository extends JpaRepository<TelemetryEntity, UUID> {

  interface StatisticsProjection {

    java.math.BigDecimal getAverageTemperature();

    java.math.BigDecimal getMinimumTemperature();

    java.math.BigDecimal getMaximumTemperature();

    java.math.BigDecimal getAverageEnergyConsumption();

    java.math.BigDecimal getMinimumEnergyConsumption();

    java.math.BigDecimal getMaximumEnergyConsumption();
  }

  Page<TelemetryEntity> findByDeviceIdAndEventTimestampBetweenOrderByEventTimestampDescEventIdDesc(
      UUID deviceId,
      Instant start,
      Instant end,
      Pageable pageable);

  @Query(value = """
      SELECT CAST(AVG(temperature) AS DECIMAL(19, 4)) AS averageTemperature,
             MIN(temperature) AS minimumTemperature,
             MAX(temperature) AS maximumTemperature,
             CAST(AVG(energy_consumption) AS DECIMAL(19, 4)) AS averageEnergyConsumption,
             MIN(energy_consumption) AS minimumEnergyConsumption,
             MAX(energy_consumption) AS maximumEnergyConsumption
        FROM telemetry_readings
       WHERE device_id = :deviceId
         AND event_timestamp BETWEEN :start AND :end
      """, nativeQuery = true)
  StatisticsProjection findStatistics(
      @Param("deviceId") UUID deviceId,
      @Param("start") Instant start,
      @Param("end") Instant end);
}
