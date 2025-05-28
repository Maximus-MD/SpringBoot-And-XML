package com.cedacri.car_app.utils;

import com.cedacri.car_app.dto.OwnerDto;

import static com.cedacri.car_app.utils.CarDTOUtils.getPreparedCarDtoList;

public class OwnerDTOUtils {
    public static OwnerDto getPreparedOwnerDto(){
        return new OwnerDto(
                "Dumitru",
                "Cebotaru",
                getPreparedCarDtoList()
        );
    }
}
