package com.cedacri.car_app.services;

import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.dto.ResponseDto;

import java.util.List;

public interface OwnerService {

    OwnerDto getOwnerById(String uuid);

    List<OwnerDto> getAllOwners();

    OwnerDto saveOwner(OwnerDto ownerDto);

    ResponseDto deleteOwnerByUuid(String uuid);

    ResponseDto addOwnerToCar(String vin, String userId);
}
