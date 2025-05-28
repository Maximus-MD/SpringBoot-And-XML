package com.cedacri.car_app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record OwnerDto(

        @NotNull
        String firstName,

        @NotNull
        String lastName,

        List<CarDto> cars
) {
}
