package com.cedacri.car_app.services;

import com.cedacri.car_app.dto.CarRequestDto;
import com.cedacri.car_app.dto.CarResponseDto;
import com.cedacri.car_app.dto.ResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {
    CarResponseDto getCarByVin(String vin);

    List<CarResponseDto> getAllCars();

    CarResponseDto saveCar(CarRequestDto carRequestDto);

    ResponseDto deleteCarByVin(String vin);

    String getVolumeInLiterByVin(String vin);
}
