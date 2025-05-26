package com.cedacri.car_app.services.impl;

import com.cedacri.car_app.dto.CarDto;
import com.cedacri.car_app.dto.ResponseDto;
import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.exceptions.CarNotFoundException;
import com.cedacri.car_app.repositories.CarRepository;
import com.cedacri.car_app.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public CarDto getCarByVin(String vin) {
        Car car = carRepository.getById(vin)
                .orElseThrow(() -> new CarNotFoundException(String.format("Car VIN %s not found.", vin)));

        return convertToDto(car);
    }

    @Override
    public ResponseDto saveCar(CarDto carDto) {
        Car car = convertToCar(carDto);
        carRepository.save(car);

        return new ResponseDto(true);
    }

    @Override
    public List<CarDto> getCars() {
        return carRepository.getAll().stream().map(this::convertToDto).toList();
    }

    @Override
    public ResponseDto deleteCarByVin(String vin) {
        Car car = carRepository.getById(vin)
                .orElseThrow(() -> new CarNotFoundException(String.format("Car VIN %s not found.", vin)));

        carRepository.removeById(car.getVinCode());

        return new ResponseDto(true);
    }

    CarDto convertToDto(Car car) {
        return CarDto.builder()
                .vin(car.getVinCode())
                .name(car.getName())
                .model(car.getModel())
                .date(car.getDate())
                .engineVolume(car.getEngineVolume())
                .enginePower(car.getEnginePower())
                .fuelType(car.getFuelType())
                .transmission(car.getTransmission())
                .type(car.getType())
                .numOfSeats(car.getNumSeats())
                .maxSpeed(car.getMaxSpeed())
                .build();
    }

    Car convertToCar(CarDto carDto){
        return Car.builder()
                .vinCode(carDto.vin())
                .name(carDto.name())
                .model(carDto.model())
                .date(carDto.date())
                .engineVolume(carDto.engineVolume())
                .enginePower(carDto.enginePower())
                .fuelType(carDto.fuelType())
                .transmission(carDto.transmission())
                .type(carDto.type())
                .numSeats(carDto.numOfSeats())
                .maxSpeed(carDto.maxSpeed())
                .build();
    }
}
