package com.cedacri.car_app.controller;

import com.cedacri.car_app.dto.CarRequestDto;
import com.cedacri.car_app.dto.CarResponseDto;
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
    public ResponseEntity<CarResponseDto> getCar(@Valid @PathVariable("vin") String vin){
        CarResponseDto response = carService.getCarByVin(vin);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<CarResponseDto> saveCar(@Valid @RequestBody final CarRequestDto carRequestDto){
        return ResponseEntity.ok(carService.saveCar(carRequestDto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CarResponseDto>> getCars(){
        return ResponseEntity.ok(carService.getAllCars());
    }

    @DeleteMapping("/delete/{vin}")
    public ResponseEntity<ResponseDto> deleteCar(@PathVariable("vin") String vin){
        return ResponseEntity.ok(carService.deleteCarByVin(vin));
    }

    @GetMapping("/get-volume/{vin}")
    public ResponseEntity<String> getVolume(@PathVariable("vin") String vin){
        return ResponseEntity.ok(carService.getVolumeInLiterByVin(vin));
    }
}
