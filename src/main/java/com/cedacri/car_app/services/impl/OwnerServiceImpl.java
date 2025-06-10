package com.cedacri.car_app.services.impl;

import com.cedacri.car_app.dto.CarRequestDto;
import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.dto.ResponseDto;
import com.cedacri.car_app.entities.Car;
import com.cedacri.car_app.entities.Owner;
import com.cedacri.car_app.exceptions.CarNotFoundException;
import com.cedacri.car_app.exceptions.OwnerNotFoundException;
import com.cedacri.car_app.mapper.CarMapper;
import com.cedacri.car_app.mapper.OwnerMapper;
import com.cedacri.car_app.repositories.CarRepository;
import com.cedacri.car_app.repositories.OwnerRepository;
import com.cedacri.car_app.services.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;

    private final CarRepository carRepository;

    private final CarServiceImpl carService;

    @Override
    public OwnerDto saveOwner(OwnerDto ownerDto) {
        Owner owner = OwnerMapper.convertToOwner(ownerDto);

        ownerRepository.save(owner);

        if (owner.getCars() != null) {
            for (Car car : owner.getCars()) {
                car.setOwner(owner);
                CarRequestDto carRequestDto = CarMapper.convertToRequestDto(car);
                carService.saveCar(carRequestDto);
            }
        }

        return ownerDto;
    }

    @Override
    public OwnerDto getOwnerById(String uuid) {
        Owner owner = ownerRepository.getById(uuid)
                .orElseThrow(() -> new OwnerNotFoundException(String.format("Owner with uuid %s not found.", uuid)));

        return OwnerMapper.convertToDto(owner);
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
        return ownerRepository.getAll().stream().map(OwnerMapper::convertToDto).toList();
    }


    @Override
    public ResponseDto deleteOwnerByUuid(String uuid) {
        Owner owner = ownerRepository.getById(uuid)
                .orElseThrow(() -> new OwnerNotFoundException(String.format("Owner with uuid %s not found.", uuid)));

        for (Car car : owner.getCars()) {
            car.setOwner(null);
            carRepository.save(car);
        }

        ownerRepository.removeById(owner.getUuid());

        return new ResponseDto(true);
    }
}
