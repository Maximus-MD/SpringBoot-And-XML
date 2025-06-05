package com.cedacri.car_app.services.impl;

import com.cedacri.car_app.dto.CarDto;
import com.cedacri.car_app.dto.ResponseDto;
import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.entities.enums.CarTypeEnum;
import com.cedacri.car_app.entities.enums.FuelTypeEnum;
import com.cedacri.car_app.entities.enums.TransmissionEnum;
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
    public CarDto saveCar(CarDto carDto) {
        Car car = convertToCar(carDto);

        setTypeBySeatsNumber(car);
        setTypeByVolume(car);

        carRepository.save(car);

        return convertToDto(car);
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.getAll().stream().map(this::convertToDto).toList();
    }

    @Override
    public ResponseDto deleteCarByVin(String vin) {
        Car car = carRepository.getById(vin)
                .orElseThrow(() -> new CarNotFoundException(String.format("Car VIN %s not found.", vin)));

        carRepository.removeById(car.getVinCode());

        return new ResponseDto(true);
    }

    @Override
    public Double getVolumeInLiterByVin(String vin) {
        Car car = carRepository.getById(vin)
                .orElseThrow(() -> new CarNotFoundException(String.format("Car VIN %s not found.", vin)));

        return (double) car.getEngineVolume() / 1000;
    }

    private void setTypeByVolume(Car car){
        if(car.getEngineVolume() >= 10000){
            car.setType(CarTypeEnum.TRUCK);
        } else if (car.getEngineVolume() < 900 && car.getEngineVolume() >= 600){
            car.setType(CarTypeEnum.KEI_CAR);
        } else if (car.getEngineVolume() == 0){
            car.setType(CarTypeEnum.ELECTRIC_CAR);
            car.setFuelType(FuelTypeEnum.ELECTRIC);
            car.setTransmission(TransmissionEnum.AUTOMATIC);
        }
    }

    private void setTypeBySeatsNumber(Car car){
        if(car.getNumSeats() == 2 && car.getDoorsNum() == 2){
            car.setType(CarTypeEnum.ROADSTER);
        } else if (car.getNumSeats() == 4 && car.getDoorsNum() == 2){
            car.setType(CarTypeEnum.COUPE);
        } else if (car.getNumSeats() == 7){
            car.setType(CarTypeEnum.VAN);
        } else if (car.getNumSeats() == 4 && car.getDoorsNum() == 4){
            car.setType(CarTypeEnum.SEDAN);
        } else if (car.getNumSeats() == 5 && car.getDoorsNum() == 5){
            car.setType(CarTypeEnum.UNIVERSAL);
        }
    }

    CarDto convertToDto(Car car) {
        return CarDto.builder()
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

    Car convertToCar(CarDto carDto){
        return Car.builder()
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
                .build();
    }
}
