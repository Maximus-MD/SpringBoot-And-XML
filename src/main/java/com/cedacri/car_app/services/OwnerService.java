package com.cedacri.car_app.services;

import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.dto.ResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OwnerService {

    OwnerDto getOwnerById(String uuid);

    List<OwnerDto> getOwners();

    ResponseDto saveOwner(OwnerDto ownerDto);

    ResponseDto deleteOwnerByUuid(String uuid);

    ResponseDto addOwnerToCar(String vin, String userId);
}
