package com.cedacri.car_app.controller.car_request_handlers;

import com.cedacri.car_app.dto.CarRequestDto;
import com.cedacri.car_app.dto.CarResponseDto;
import com.cedacri.car_app.controller.validator.ValidatorUtil;
import com.cedacri.car_app.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.web.HttpRequestHandler;

import java.io.IOException;

public class CarSaveHandler implements HttpRequestHandler {

    private final CarService carService;

    private final Validator validator;

    public CarSaveHandler(CarService carService, Validator validator) {
        this.carService = carService;
        this.validator = validator;
    }

    @Override
    public void handleRequest(@Valid HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CarRequestDto carRequestDto = mapper.readValue(request.getInputStream(), CarRequestDto.class);

        ValidatorUtil validatorUtil = new ValidatorUtil(validator, mapper);
        validatorUtil.validateAndRespond(response, carRequestDto);

        CarResponseDto saved = carService.saveCar(carRequestDto);
        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), saved);
    }
}
