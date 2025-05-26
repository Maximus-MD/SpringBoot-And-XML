package com.cedacri.car_app.services;

import com.cedacri.car_app.dto.CarDto;
import com.cedacri.car_app.dto.ResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {
    CarDto getCarByVin(String vin);

    List<CarDto> getCars();

    ResponseDto saveCar(CarDto carDto);

    ResponseDto deleteCarByVin(String vin);
}
