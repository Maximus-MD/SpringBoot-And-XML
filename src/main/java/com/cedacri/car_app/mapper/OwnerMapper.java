package com.cedacri.car_app.mapper;

import com.cedacri.car_app.dto.CarRequestDto;
import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.entities.Owner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OwnerMapper {
    public static Owner convertToOwner(OwnerDto ownerDto) {
        List<Car> cars = ownerDto.cars().stream()
                .map(carDto -> Car.builder()
                        .vinCode(carDto.vin())
                        .name(carDto.name())
                        .model(carDto.model())
                        .manufactureYear(carDto.manufactureYear())
                        .engineVolume(carDto.engineVolume())
                        .enginePower(carDto.enginePower())
                        .fuelType(carDto.fuelType())
                        .transmission(carDto.transmission())
                        .numSeats(carDto.numOfSeats())
                        .doorsNum(carDto.doorsNum())
                        .maxSpeed(carDto.maxSpeed())
                        .build())
                .toList();

        return Owner.builder()
                .firstName(ownerDto.firstName())
                .lastName(ownerDto.lastName())
                .cars(cars)
                .build();
    }

    public static OwnerDto convertToDto(Owner owner) {
        List<CarRequestDto> cars = owner.getCars().stream()
                .map(car -> CarRequestDto.builder()
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
                        .build()
                ).toList();

        return OwnerDto.builder()
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .cars(cars)
                .build();
    }
}
