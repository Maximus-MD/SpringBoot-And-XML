package com.cedacri.car_app.controller.owner_request_handlers;

import com.cedacri.car_app.dto.ResponseDto;
import com.cedacri.car_app.services.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.HttpRequestHandler;

import java.io.IOException;

public class OwnerDeleteHandler implements HttpRequestHandler {
    private final OwnerService ownerService;

    public OwnerDeleteHandler(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String uuid = uri.substring(uri.lastIndexOf("/") + 1);

        ResponseDto result = ownerService.deleteOwnerByUuid(uuid);

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), result);
    }
}
