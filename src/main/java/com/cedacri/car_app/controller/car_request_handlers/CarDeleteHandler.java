package com.cedacri.car_app.controller.car_request_handlers;

import com.cedacri.car_app.dto.ResponseDto;
import com.cedacri.car_app.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.HttpRequestHandler;

import java.io.IOException;

public class CarDeleteHandler implements HttpRequestHandler {

    private final CarService carService;

    public CarDeleteHandler(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String vin = uri.substring(uri.lastIndexOf("/") + 1);

        ResponseDto result = carService.deleteCarByVin(vin);

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), result);
    }
}