package com.cedacri.car_app.controller.owner_request_handlers;

import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.services.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.HttpRequestHandler;

import java.io.IOException;

public class OwnerGetByIdHandler implements HttpRequestHandler {
    private final OwnerService ownerService;

    public OwnerGetByIdHandler(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = request.getRequestURI();
        String uuid = path.substring(path.lastIndexOf("/") + 1);

        OwnerDto owner = ownerService.getOwnerById(uuid);

        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), owner);
    }
}
