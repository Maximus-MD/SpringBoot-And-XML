package com.cedacri.car_app.utils;

import com.cedacri.car_app.dto.OwnerDto;

import java.util.List;

import static com.cedacri.car_app.utils.CarRequestDtoUtils.getPreparedCarRequestDto;
import static com.cedacri.car_app.utils.CarRequestDtoUtils.getPreparedCarRequestDtoList;

public class OwnerDTOUtils {
    public static OwnerDto getPreparedOwnerDto() {
        return new OwnerDto(
                "Dumitru",
                "Cebotaru",
                getPreparedCarRequestDtoList()
        );
    }

    public static List<OwnerDto> getPreparedOwnerDtoList() {
        return List.of(getPreparedOwnerDto(),
                new OwnerDto("Maria", "Cojocaru",
                        List.of(getPreparedCarRequestDto())));
    }
}
