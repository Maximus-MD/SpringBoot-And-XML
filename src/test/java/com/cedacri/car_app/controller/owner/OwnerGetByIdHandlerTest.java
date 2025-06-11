package com.cedacri.car_app.controller.owner;

import com.cedacri.car_app.controller.owner_request_handlers.OwnerGetByIdHandler;
import com.cedacri.car_app.dto.CarResponseDto;
import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.services.OwnerService;
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

import static com.cedacri.car_app.utils.CarResponseDtoUtils.getPreparedCarResponseDto;
import static com.cedacri.car_app.utils.OwnerDTOUtils.getPreparedOwnerDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnerGetByIdHandlerTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerGetByIdHandler ownerGetByIdHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenHttpServletRequest_whenGetOwnerById_thenReturnsOwnerDto() throws IOException {
        OwnerDto responseDto = getPreparedOwnerDto();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getRequestURI()).thenReturn("/owner/c3b0d2a2-cf75-4745-a0b6-6c9d83d83d4d");
        when(ownerService.getOwnerById(any())).thenReturn(responseDto);

        ByteArrayOutputStream responseContent = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new DelegatingServletOutputStream(responseContent);
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        ownerGetByIdHandler.handleRequest(request, response);

        String expectedJson = objectMapper.writeValueAsString(responseDto);
        String actualJson = responseContent.toString();

        verify(ownerService).getOwnerById("c3b0d2a2-cf75-4745-a0b6-6c9d83d83d4d");

        assertThat(actualJson).isEqualTo(expectedJson);
    }
}
