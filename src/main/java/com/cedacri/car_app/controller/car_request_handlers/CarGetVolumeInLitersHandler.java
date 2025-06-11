package com.cedacri.car_app.controller.car_request_handlers;

import com.cedacri.car_app.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.HttpRequestHandler;

import java.io.IOException;

public class CarGetVolumeInLitersHandler implements HttpRequestHandler {
    private final CarService carService;

    public CarGetVolumeInLitersHandler(CarService carService) {
        this.carService = carService;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getRequestURI();
        String vin = path.substring(path.lastIndexOf("/") + 1);

        String volume = carService.getVolumeInLiterByVin(vin);

        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), volume);
    }
}
