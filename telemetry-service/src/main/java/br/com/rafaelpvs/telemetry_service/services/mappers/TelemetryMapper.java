package br.com.rafaelpvs.telemetry_service.services.mappers;

import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import br.com.rafaelpvs.telemetry_service.consumers.telemetry.events.TelemetryReceivedEvent;
import br.com.rafaelpvs.telemetry_service.entities.TelemetryEntity;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TelemetryMapper {

  @Mapping(target = "eventId", source = "event.eventId")
  @Mapping(target = "deviceId", source = "event.deviceId")
  @Mapping(target = "temperature", source = "event.temperature")
  @Mapping(target = "energyConsumption", source = "event.energyConsumption")
  @Mapping(target = "eventTimestamp", source = "event.timestamp")
  @Mapping(target = "receivedAt", source = "receivedAt")
  TelemetryEntity toEntity(TelemetryReceivedEvent event, Instant receivedAt);
}
