package com.cedacri.car_app.dto;

import lombok.Builder;

@Builder
public record ResponseDto(
        boolean success
) {}
