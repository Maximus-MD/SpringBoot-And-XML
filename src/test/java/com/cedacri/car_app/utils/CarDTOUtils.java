package com.cedacri.car_app.utils;

import com.cedacri.car_app.dto.CarDto;
import com.cedacri.car_app.entities.enums.FuelTypeEnum;
import com.cedacri.car_app.entities.enums.TransmissionEnum;

import java.util.List;

public class CarDTOUtils {
    public static CarDto getPreparedCarDto() {
        return new CarDto(
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

    public static List<CarDto> getPreparedCarDtoList() {
        return List.of(new CarDto(
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
                new CarDto(
                        "WDBUF56X48B123654",
                        "Nissan",
                        "Skyline",
                        1999,
                        2600,
                        320,
                        FuelTypeEnum.PETROL,
                        TransmissionEnum.MANUAL,
                        4, 2, 260
                ));
    }
}
