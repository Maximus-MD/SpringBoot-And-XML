package com.cedacri.car_app.controller.car_request_handlers;

import com.cedacri.car_app.dto.CarResponseDto;
import com.cedacri.car_app.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.HttpRequestHandler;

import java.io.IOException;
import java.util.List;

public class CarGetAllHandler implements HttpRequestHandler {

    private final CarService carService;

    public CarGetAllHandler(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<CarResponseDto> cars = carService.getAllCars();

        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), cars);
    }
}
