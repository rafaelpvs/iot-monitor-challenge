package br.com.rafaelpvs.device_service.controllers.device.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import br.com.rafaelpvs.device_service.controllers.device.dtos.CreateDeviceRequest;
import br.com.rafaelpvs.device_service.controllers.device.dtos.CreateDeviceResponse;
import br.com.rafaelpvs.device_service.entities.DeviceEntity;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DeviceMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  DeviceEntity toEntity(CreateDeviceRequest request);

  CreateDeviceResponse toCreateResponse(DeviceEntity device);
}
