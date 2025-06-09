package com.cedacri.car_app.utils;

import com.cedacri.car_app.dto.CarRequestDto;
import com.cedacri.car_app.entities.enums.FuelTypeEnum;
import com.cedacri.car_app.entities.enums.TransmissionEnum;

import java.util.List;

public class CarRequestDtoUtils {
    public static CarRequestDto getPreparedCarRequestDto() {
        return new CarRequestDto(
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

    public static CarRequestDto getPreparedCarRequestDtoRoadster() {
        return new CarRequestDto(
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

    public static CarRequestDto getPreparedCarRequestDtoElectric() {
        return new CarRequestDto(
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

    public static CarRequestDto getPreparedCarRequestDtoVan() {
        return new CarRequestDto(
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

    public static CarRequestDto getPreparedCarRequestDtoTruck() {
        return new CarRequestDto(
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

    public static CarRequestDto getPreparedCarRequestDtoKeiCar() {
        return new CarRequestDto(
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

    public static CarRequestDto getPreparedCarRequestDtoUniversalCar() {
        return new CarRequestDto(
                "EA11R56X48B123654",
                "VW",
                "Passat B5",
                2001,
                1900,
                130,
                FuelTypeEnum.DIESEL,
                TransmissionEnum.MANUAL,
                5, 5, 210
        );
    }

    public static List<CarRequestDto> getPreparedCarRequestDtoList() {
        return List.of(new CarRequestDto(
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
                new CarRequestDto(
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
