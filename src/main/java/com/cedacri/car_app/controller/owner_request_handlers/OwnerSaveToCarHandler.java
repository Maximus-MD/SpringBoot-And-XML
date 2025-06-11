package com.cedacri.car_app.controller.owner_request_handlers;

import com.cedacri.car_app.dto.ResponseDto;
import com.cedacri.car_app.services.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.HttpRequestHandler;

import java.io.IOException;

public class OwnerSaveToCarHandler implements HttpRequestHandler {
    private final OwnerService ownerService;

    public OwnerSaveToCarHandler(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String vin = request.getParameter("vin");
        String uuid = request.getParameter("uuid");

        ResponseDto responseDto = ownerService.addOwnerToCar(vin, uuid);

        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), responseDto);
    }
}