package com.cedacri.car_app.utils;

import com.cedacri.car_app.dto.CarResponseDto;
import com.cedacri.car_app.entities.enums.FuelTypeEnum;
import com.cedacri.car_app.entities.enums.TransmissionEnum;

import java.util.List;

public class CarResponseDtoUtils {
    public static CarResponseDto getPreparedCarResponseDto() {
        return new CarResponseDto(
                "WDBUF56X48B123654",
                "Mazda",
                "RX-7",
                1997,
                1300,
                280,
                FuelTypeEnum.PETROL,
                TransmissionEnum.MANUAL,
                4, 2, 260
        );
    }

    public static CarResponseDto getPreparedCarResponseDtoRoadster() {
        return new CarResponseDto(
                "WDBUF56X48B123654",
                "Honda",
                "S2000",
                2005,
                2200,
                240,
                FuelTypeEnum.PETROL,
                TransmissionEnum.MANUAL,
                2, 2, 250
        );
    }

    public static CarResponseDto getPreparedCarResponseDtoElectric() {
        return new CarResponseDto(
                "WDBUF56X48B123654",
                "Mercedes",
                "EQS",
                2024,
                0,
                450,
                null,
                null,
                4, 4, 249
        );
    }

    public static CarResponseDto getPreparedCarResponseDtoVan() {
        return new CarResponseDto(
                "WAUZZZ8K9BA12888",
                "Kia",
                "Carnival",
                2021,
                2197,
                180,
                FuelTypeEnum.DIESEL,
                TransmissionEnum.AUTOMATIC,
                7,
                5,
                185
        );
    }

    public static CarResponseDto getPreparedCarResponseDtoTruck() {
        return new CarResponseDto(
                "WDBUF56X48B123654",
                "Scania",
                "R480",
                2006,
                12740,
                560,
                FuelTypeEnum.DIESEL,
                TransmissionEnum.AUTOMATIC,
                4, 2, 89
        );
    }

    public static CarResponseDto getPreparedCarResponseDtoKeiCar() {
        return new CarResponseDto(
                "EA11R56X48B123654",
                "Suzuki",
                "Cappucino",
                1995,
                657,
                63,
                FuelTypeEnum.PETROL,
                TransmissionEnum.MANUAL,
                2, 2, 140
        );
    }

    public static List<CarResponseDto> getPreparedCarResponseDtoList() {
        return List.of(new CarResponseDto(
                        "WDBUF56X48B123654",
                        "Mazda",
                        "RX-7",
                        1997,
                        1300,
                        280,
                        FuelTypeEnum.PETROL,
                        TransmissionEnum.MANUAL,
                        4, 2, 260
                ),
                new CarResponseDto(
                        "WDBUF56X48B123777",
                        "Volvo",
                        "FH16",
                        1997,
                        11000,
                        480,
                        FuelTypeEnum.DIESEL,
                        TransmissionEnum.AUTOMATIC,
                        4, 2, 90
                ));
    }
}
