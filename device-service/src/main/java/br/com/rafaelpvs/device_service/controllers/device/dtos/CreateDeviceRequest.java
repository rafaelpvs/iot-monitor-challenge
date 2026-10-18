package br.com.rafaelpvs.device_service.controllers.device.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateDeviceRequest(
    @NotBlank @Size(max = 150) String name,

    @NotBlank @Size(max = 50) String type,

    @Size(max = 255) String location,

    @NotNull Boolean active,

    @Digits(integer = 8, fraction = 2) BigDecimal minimumLimit,

    @Digits(integer = 8, fraction = 2) BigDecimal maximumLimit) {

  @AssertTrue(message = "maximumLimit must be greater than or equal to minimumLimit")
  public boolean isLimitRangeValid() {
    return minimumLimit == null
        || maximumLimit == null
        || maximumLimit.compareTo(minimumLimit) >= 0;
  }
}
