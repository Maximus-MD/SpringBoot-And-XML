package com.cedacri.car_app.controller;

import com.cedacri.car_app.dto.CarDto;
import com.cedacri.car_app.dto.ResponseDto;
import com.cedacri.car_app.services.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/{vin}")
    public ResponseEntity<CarDto> getCar(@Valid @PathVariable("vin") String vin){
        CarDto response = carService.getCarByVin(vin);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<CarDto> saveCar(@Valid @RequestBody CarDto carDto){
        return ResponseEntity.ok(carService.saveCar(carDto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CarDto>> getCars(){
        List<CarDto> cars = carService.getAllCars();
        return ResponseEntity.ok(cars);
    }

    @DeleteMapping("/delete/{vin}")
    public ResponseEntity<ResponseDto> deleteCar(@PathVariable("vin") String vin){
        return ResponseEntity.ok(carService.deleteCarByVin(vin));
    }

    @GetMapping("/get-volume/{vin}")
    public ResponseEntity<Double> getVolume(@PathVariable("vin") String vin){
        return ResponseEntity.ok(carService.getVolumeInLiterByVin(vin));
    }
}
