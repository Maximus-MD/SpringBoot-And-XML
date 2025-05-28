package com.cedacri.car_app.controller;

import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.dto.ResponseDto;
import com.cedacri.car_app.services.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping("/{uuid}")
    public ResponseEntity<OwnerDto> getOwner(@Valid @PathVariable("uuid") String uuid){
        OwnerDto response = ownerService.getOwnerById(uuid);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<OwnerDto> saveOwner(@Valid @RequestBody OwnerDto ownerDto){
        return ResponseEntity.ok(ownerService.saveOwner(ownerDto));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OwnerDto>> getOwners(){
        List<OwnerDto> owners = ownerService.getAllOwners();
        return ResponseEntity.ok(owners);
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<ResponseDto> deleteOwner(@PathVariable("uuid") String uuid){
        return ResponseEntity.ok(ownerService.deleteOwnerByUuid(uuid));
    }

    @PostMapping("/add-owner")
    public ResponseEntity<ResponseDto> addOwnerToCar(@RequestParam String vin, @RequestParam String userId){
        return ResponseEntity.ok(ownerService.addOwnerToCar(vin, userId));
    }
}
