package com.cedacri.car_app.services.impl;

import com.cedacri.car_app.dto.CarRequestDto;
import com.cedacri.car_app.dto.CarResponseDto;
import com.cedacri.car_app.dto.ResponseDto;
import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.entities.enums.CarTypeEnum;
import com.cedacri.car_app.entities.enums.FuelTypeEnum;
import com.cedacri.car_app.entities.enums.TransmissionEnum;
import com.cedacri.car_app.exceptions.CarNotFoundException;
import com.cedacri.car_app.mapper.CarMapper;
import com.cedacri.car_app.repositories.CarRepository;
import com.cedacri.car_app.services.CarService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static com.cedacri.car_app.mapper.CarMapper.buildCarResponseDto;

public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public CarResponseDto saveCar(CarRequestDto carRequestDto) {
        Car car = CarMapper.convertToCar(carRequestDto);

        setTypeBySeatsNumber(car);
        setTypeByVolume(car);

        carRepository.save(car);

        return buildCarResponseDto(carRequestDto, car);
    }

    @Override
    public CarResponseDto getCarByVin(String vin) {
        Car car = carRepository.getById(vin)
                .orElseThrow(() -> new CarNotFoundException(String.format("Car VIN %s not found.", vin)));

        return CarMapper.convertToResponseDto(car);
    }

    @Override
    public List<CarResponseDto> getAllCars() {
        return carRepository.getAll().stream()
                .map(CarMapper::convertToResponseDto).toList();
    }

    @Override
    public ResponseDto deleteCarByVin(String vin) {
        Car car = carRepository.getById(vin)
                .orElseThrow(() -> new CarNotFoundException(String.format("Car VIN %s not found.", vin)));

        carRepository.removeById(car.getVinCode());

        return new ResponseDto(true);
    }

    @Override
    public String getVolumeInLiterByVin(String vin) {
        Car car = carRepository.getById(vin)
                .orElseThrow(() -> new CarNotFoundException(String.format("Car VIN %s not found.", vin)));

        return "Volume of car " + car.getName() + " " + car.getModel() + " is " + BigDecimal.valueOf(car.getEngineVolume())
                .divide(BigDecimal.valueOf(1000), 1, RoundingMode.HALF_UP)
                .doubleValue() + "L.";
    }

    private void setTypeByVolume(Car car) {
        if (car.getEngineVolume() >= 10000) {
            car.setType(CarTypeEnum.TRUCK);
        } else if (car.getEngineVolume() < 900 && car.getEngineVolume() >= 600) {
            car.setType(CarTypeEnum.KEI_CAR);
        } else if (car.getEngineVolume() == 0) {
            car.setType(CarTypeEnum.ELECTRIC_CAR);
            car.setFuelType(FuelTypeEnum.ELECTRIC);
            car.setTransmission(TransmissionEnum.AUTOMATIC);
        }
    }

    private void setTypeBySeatsNumber(Car car) {
        if (car.getNumSeats() == 2 && car.getDoorsNum() == 2) {
            car.setType(CarTypeEnum.ROADSTER);
        } else if (car.getNumSeats() == 4 && car.getDoorsNum() == 2) {
            car.setType(CarTypeEnum.COUPE);
        } else if (car.getNumSeats() == 7) {
            car.setType(CarTypeEnum.VAN);
        } else if (car.getNumSeats() == 4 && car.getDoorsNum() == 4) {
            car.setType(CarTypeEnum.SEDAN);
        } else if (car.getNumSeats() == 5 && car.getDoorsNum() == 5) {
            car.setType(CarTypeEnum.UNIVERSAL);
        }
    }
}
