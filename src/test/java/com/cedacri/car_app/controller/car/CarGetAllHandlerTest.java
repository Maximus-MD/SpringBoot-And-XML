package com.cedacri.car_app.controller.car;

import com.cedacri.car_app.controller.car_request_handlers.CarGetAllHandler;
import com.cedacri.car_app.dto.CarResponseDto;
import com.cedacri.car_app.services.CarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.DelegatingServletOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.cedacri.car_app.utils.CarResponseDtoUtils.getPreparedCarResponseDtoList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarGetAllHandlerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarGetAllHandler carGetAllHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenHttpServletRequest_whenGetAllCars_thenReturnsCarList() throws IOException {
        List<CarResponseDto> responseDtoList = getPreparedCarResponseDtoList();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(carService.getAllCars()).thenReturn(responseDtoList);

        ByteArrayOutputStream responseContent = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new DelegatingServletOutputStream(responseContent);
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        carGetAllHandler.handleRequest(request, response);

        String expectedJson = objectMapper.writeValueAsString(responseDtoList);
        String actualJson = responseContent.toString();

        assertThat(actualJson).isEqualTo(expectedJson);
    }
}
