package com.cedacri.car_app.controller.owner_request_handlers;

import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.controller.validator.ValidatorUtil;
import com.cedacri.car_app.services.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validator;
import org.springframework.web.HttpRequestHandler;

import java.io.IOException;

public class OwnerSaveHandler implements HttpRequestHandler {

    private final OwnerService ownerService;

    private final Validator validator;

    public OwnerSaveHandler(OwnerService ownerService, Validator validator) {
        this.ownerService = ownerService;
        this.validator = validator;
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        OwnerDto ownerDto = mapper.readValue(request.getInputStream(), OwnerDto.class);

        ValidatorUtil validate = new ValidatorUtil(validator, mapper);
        validate.validateAndRespond(response, ownerDto);

        OwnerDto saved = ownerService.saveOwner(ownerDto);
        response.setContentType("application/json");
        mapper.writeValue(response.getOutputStream(), saved);
    }
}
