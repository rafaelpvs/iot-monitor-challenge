package br.com.rafaelpvs.alert_service.controllers.alert.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import br.com.rafaelpvs.alert_service.controllers.alert.dtos.AlertResponse;
import br.com.rafaelpvs.alert_service.entities.AlertEntity;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AlertMapper {

  @Mapping(target = "timestamp", source = "eventTimestamp")
  AlertResponse toResponse(AlertEntity alert);
}
