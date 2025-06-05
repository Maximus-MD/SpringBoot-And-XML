package com.cedacri.car_app.dto;

import com.cedacri.car_app.entities.enums.FuelTypeEnum;
import com.cedacri.car_app.entities.enums.TransmissionEnum;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CarDto(

        @Size(min = 17, max = 17)
        @Pattern(regexp = "(?=.*\\d|[A-Z])(?=.*[A-Z])[A-Z0-9]{17}")
        @NotBlank(message = "VIN can't be empty.")
        String vin,

        @NotBlank(message = "Car name must not be empty.")
        String name,

        @NotBlank(message = "Car model must not be empty.")
        String model,

        @NotNull(message = "Manufacture year must not be null.")
        Integer manufactureYear,

        @NotNull(message = "Engine volume must not be null.")
        @Min(0)
        Integer engineVolume,

        @NotNull(message = "Engine power must not be null.")
        Integer enginePower,

        @NotNull(message = "Fuel type must not be null.")
        FuelTypeEnum fuelType,

        @NotNull(message = "Transmission type must not be null.")
        TransmissionEnum transmission,

        @NotNull(message = "Number of seats must not be null.")
        Integer numOfSeats,

        @NotNull(message = "Number of door must not be null.")
        Integer doorsNum,

        Integer maxSpeed
) {}
