package com.cedacri.car_app.controller.car_request_handlers;

import com.cedacri.car_app.dto.CarResponseDto;
import com.cedacri.car_app.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.HttpRequestHandler;

import java.io.IOException;

public class CarGetByVinHandler implements HttpRequestHandler {

    private final CarService carService;

    public CarGetByVinHandler(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getRequestURI();
        String vin = path.substring(path.lastIndexOf("/") + 1);

        CarResponseDto car = carService.getCarByVin(vin);

        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), car);
    }
}
