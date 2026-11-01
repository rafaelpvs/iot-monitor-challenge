package br.com.rafaelpvs.telemetry_service.controllers.telemetry.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import br.com.rafaelpvs.telemetry_service.controllers.telemetry.dtos.TelemetryResponse;
import br.com.rafaelpvs.telemetry_service.entities.TelemetryEntity;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TelemetryResponseMapper {

  @Mapping(target = "timestamp", source = "eventTimestamp")
  TelemetryResponse toResponse(TelemetryEntity telemetry);
}
