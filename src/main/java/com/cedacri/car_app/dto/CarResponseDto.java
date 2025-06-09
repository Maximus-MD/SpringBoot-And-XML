package com.cedacri.car_app.dto;

import com.cedacri.car_app.entities.enums.FuelTypeEnum;
import com.cedacri.car_app.entities.enums.TransmissionEnum;

import lombok.Builder;

@Builder
public record CarResponseDto(
        String vin,

        String name,

        String model,

        Integer manufactureYear,

        Integer engineVolume,

        Integer enginePower,

        FuelTypeEnum fuelType,

        TransmissionEnum transmission,

        Integer numOfSeats,

        Integer doorsNum,

        Integer maxSpeed
        ) {
}

