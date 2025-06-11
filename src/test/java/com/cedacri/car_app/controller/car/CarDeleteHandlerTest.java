package com.cedacri.car_app.controller.car;

import com.cedacri.car_app.controller.car_request_handlers.CarDeleteHandler;
import com.cedacri.car_app.dto.ResponseDto;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CarDeleteHandlerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarDeleteHandler carDeleteHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenHttpServletRequest_whenDeleteCarCars_thenReturnsResponseDto() throws IOException {
        ResponseDto responseDto = new ResponseDto(true);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/cars/delete/WDBUF56X48B123654");
        when(carService.deleteCarByVin(any())).thenReturn(responseDto);

        ByteArrayOutputStream responseContent = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new DelegatingServletOutputStream(responseContent);
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        carDeleteHandler.handleRequest(request, response);

        String expectedJson = objectMapper.writeValueAsString(responseDto);
        String actualJson = responseContent.toString();

        verify(carService).deleteCarByVin("WDBUF56X48B123654");

        assertThat(actualJson).isEqualTo(expectedJson);
    }
}
