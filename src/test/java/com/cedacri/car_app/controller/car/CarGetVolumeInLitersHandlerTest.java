package com.cedacri.car_app.controller.car;

import com.cedacri.car_app.controller.car_request_handlers.CarGetVolumeInLitersHandler;
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
public class CarGetVolumeInLitersHandlerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarGetVolumeInLitersHandler carGetVolumeInLitersHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenHttpServletRequest_whenGetCarVolumeInLiters_thenReturnsResponse() throws IOException {
        String message = "Volume of car Mazda RX-7 is 1.3L.";

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/cars/get-volume/WDBUF56X48B123654");
        when(carService.getVolumeInLiterByVin(any())).thenReturn(message);

        ByteArrayOutputStream responseContent = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new DelegatingServletOutputStream(responseContent);
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        carGetVolumeInLitersHandler.handleRequest(request, response);

        String expectedMessage = objectMapper.writeValueAsString(message);
        String actualMessage = responseContent.toString();

        verify(carService).getVolumeInLiterByVin("WDBUF56X48B123654");

        assertThat(actualMessage).isEqualTo(expectedMessage);
    }
}
