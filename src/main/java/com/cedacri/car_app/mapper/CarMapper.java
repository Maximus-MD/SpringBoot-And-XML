package com.cedacri.car_app.mapper;

import com.cedacri.car_app.dto.CarRequestDto;
import com.cedacri.car_app.dto.CarResponseDto;
import com.cedacri.car_app.entities.Car;

public class CarMapper {
    public static CarRequestDto convertToRequestDto(Car car) {
        return CarRequestDto.builder()
                .vin(car.getVinCode())
                .name(car.getName())
                .model(car.getModel())
                .manufactureYear(car.getManufactureYear())
                .engineVolume(car.getEngineVolume())
                .enginePower(car.getEnginePower())
                .fuelType(car.getFuelType())
                .transmission(car.getTransmission())
                .numOfSeats(car.getNumSeats())
                .doorsNum(car.getDoorsNum())
                .maxSpeed(car.getMaxSpeed())
                .build();
    }

    public static CarResponseDto convertToResponseDto(Car car) {
        return CarResponseDto.builder()
                .vin(car.getVinCode())
                .name(car.getName())
                .model(car.getModel())
                .manufactureYear(car.getManufactureYear())
                .engineVolume(car.getEngineVolume())
                .enginePower(car.getEnginePower())
                .fuelType(car.getFuelType())
                .transmission(car.getTransmission())
                .numOfSeats(car.getNumSeats())
                .doorsNum(car.getDoorsNum())
                .maxSpeed(car.getMaxSpeed())
                .build();
    }

    public static Car convertToCar(CarRequestDto carRequestDto){
        return Car.builder()
                .vinCode(carRequestDto.vin())
                .name(carRequestDto.name())
                .model(carRequestDto.model())
                .manufactureYear(carRequestDto.manufactureYear())
                .engineVolume(carRequestDto.engineVolume())
                .enginePower(carRequestDto.enginePower())
                .fuelType(carRequestDto.fuelType())
                .transmission(carRequestDto.transmission())
                .numSeats(carRequestDto.numOfSeats())
                .doorsNum(carRequestDto.doorsNum())
                .maxSpeed(carRequestDto.maxSpeed())
                .build();
    }

    public static CarResponseDto buildCarResponseDto(CarRequestDto carRequestDto, Car car){
        return CarResponseDto.builder()
                .vin(carRequestDto.vin())
                .name(carRequestDto.name())
                .model(carRequestDto.model())
                .manufactureYear(carRequestDto.manufactureYear())
                .engineVolume(carRequestDto.engineVolume())
                .enginePower(carRequestDto.enginePower())
                .fuelType(car.getFuelType())
                .transmission(car.getTransmission())
                .numOfSeats(carRequestDto.numOfSeats())
                .doorsNum(carRequestDto.doorsNum())
                .maxSpeed(carRequestDto.maxSpeed())
                .build();
    }
}
