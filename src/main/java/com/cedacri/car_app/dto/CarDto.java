package com.cedacri.car_app.dto;

import com.cedacri.car_app.entities.Owner;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CarDto(

        @Size(min = 17, max = 17)
        @Pattern(regexp = "(?=.*\\d|[A-Z])(?=.*[A-Z])[A-Z0-9]{17}")
        @NotNull(message = "VIN can't be empty.")
        String vin,

        @NotNull
        String name,

        @NotNull
        String model,

        @NotNull
        int date,

        @NotNull
        int engineVolume,

        @NotNull
        int enginePower,

        @NotNull
        String fuelType,

        @NotNull
        String transmission,

        @NotNull
        String type,

        @NotNull
        int numOfSeats,

        @NotNull
        int maxSpeed
) {}
