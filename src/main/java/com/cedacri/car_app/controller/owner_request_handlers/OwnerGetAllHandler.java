package com.cedacri.car_app.controller.owner_request_handlers;

import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.services.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.HttpRequestHandler;

import java.io.IOException;
import java.util.List;

public class OwnerGetAllHandler implements HttpRequestHandler {
    private final OwnerService ownerService;

    public OwnerGetAllHandler(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<OwnerDto> owners = ownerService.getAllOwners();

        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), owners);
    }
}
