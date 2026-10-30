package br.com.rafaelpvs.ingestion_service.controllers.telemetry.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import br.com.rafaelpvs.ingestion_service.controllers.telemetry.dtos.CreateTelemetryRequest;
import br.com.rafaelpvs.ingestion_service.services.telemetry.commands.CreateTelemetryCommand;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface TelemetryMapper {

  CreateTelemetryCommand toCommand(CreateTelemetryRequest request);
}
