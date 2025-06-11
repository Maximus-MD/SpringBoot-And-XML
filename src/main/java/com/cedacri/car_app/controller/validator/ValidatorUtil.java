package com.cedacri.car_app.controller.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class ValidatorUtil {
    private final Validator validator;
    private final ObjectMapper mapper;

    public <T> void validateAndRespond(HttpServletResponse response, T dto) throws IOException {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");

            List<String> errors = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .toList();

            mapper.writeValue(response.getOutputStream(), errors);
        }
    }
}
