package br.com.rafaelpvs.device_service.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rafaelpvs.device_service.entities.DeviceEntity;
import br.com.rafaelpvs.device_service.repositories.DeviceRepository;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTests {

  @Mock
  private DeviceRepository deviceRepository;

  @InjectMocks
  private DeviceService deviceService;

  @Test
  void shouldFindDeviceById() {
    var id = UUID.randomUUID();
    var device = new DeviceEntity();
    when(deviceRepository.findById(id)).thenReturn(Optional.of(device));

    assertThat(deviceService.findById(id)).isSameAs(device);
  }

  @Test
  void shouldThrowWhenDeviceDoesNotExist() {
    var id = UUID.randomUUID();
    when(deviceRepository.findById(id)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> deviceService.findById(id))
        .isInstanceOf(DeviceNotFoundException.class)
        .hasMessage("Device not found: " + id);
  }

  @Test
  void shouldUpdateDevice() {
    var device = new DeviceEntity();
    when(deviceRepository.save(device)).thenReturn(device);

    var result = deviceService.update(device);

    verify(deviceRepository).save(device);
    assertThat(result).isSameAs(device);
  }

  @Test
  void shouldUpdateOnlyDeviceStatus() {
    var id = UUID.randomUUID();
    var device = new DeviceEntity();
    device.setActive(true);
    when(deviceRepository.findById(id)).thenReturn(Optional.of(device));
    when(deviceRepository.save(device)).thenReturn(device);

    var result = deviceService.updateStatus(id, false);

    assertThat(result.isActive()).isFalse();
    verify(deviceRepository).save(device);
  }
}
