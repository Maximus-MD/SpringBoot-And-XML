package com.cedacri.car_app.services.impl;

import com.cedacri.car_app.dto.CarDto;
import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.dto.ResponseDto;
import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.entities.Owner;
import com.cedacri.car_app.exceptions.CarNotFoundException;
import com.cedacri.car_app.exceptions.OwnerNotFoundException;
import com.cedacri.car_app.repositories.CarRepository;
import com.cedacri.car_app.repositories.OwnerRepository;
import com.cedacri.car_app.services.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    private final CarRepository carRepository;

    @Override
    public OwnerDto getOwnerById(String uuid) {
        Owner owner = ownerRepository.getById(uuid)
                .orElseThrow(() -> new OwnerNotFoundException(String.format("Owner with uuid %s not found.", uuid)));

        return convertToDto(owner);
    }

    @Override
    public ResponseDto addOwnerToCar(String vin, String userId) {
        Owner owner = ownerRepository.getById(userId)
                .orElseThrow(() -> new OwnerNotFoundException(String.format("Owner with uuid %s not found.", userId)));

        Car car = carRepository.getById(vin)
                .orElseThrow(() -> new CarNotFoundException(String.format("Car VIN %s not found.", vin)));

        car.setOwner(owner);

        owner.getCars().add(car);

        carRepository.save(car);

        return new ResponseDto(true);
    }

    @Override
    public List<OwnerDto> getAllOwners() {
        return ownerRepository.getAll().stream().map(this::convertToDto).toList();
    }

    @Override
    public OwnerDto saveOwner(OwnerDto ownerDto) {
        Owner owner = convertToOwner(ownerDto);
        ownerRepository.save(owner);

        return ownerDto;
    }

    @Override
    public ResponseDto deleteOwnerByUuid(String uuid) {
        Owner owner = ownerRepository.getById(uuid)
                .orElseThrow(() -> new OwnerNotFoundException(String.format("Owner with uuid %s not found.", uuid)));

        for(Car car : owner.getCars()){
            car.setOwner(null);
            carRepository.save(car);
        }

        ownerRepository.removeById(owner.getUuid());

        return new ResponseDto(true);
    }

    private Owner convertToOwner(OwnerDto ownerDto) {
        return Owner.builder()
                .firstName(ownerDto.firstName())
                .lastName(ownerDto.lastName())
                .build();
    }

    private OwnerDto convertToDto(Owner owner) {
        List<CarDto> cars = owner.getCars().stream()
                .map(car -> CarDto.builder()
                        .vin(car.getVinCode())
                        .name(car.getName())
                        .model(car.getModel())
                        .date(car.getDate())
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
