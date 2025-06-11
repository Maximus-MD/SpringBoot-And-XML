package com.cedacri.car_app.controller.owner;

import com.cedacri.car_app.controller.owner_request_handlers.OwnerSaveToCarHandler;
import com.cedacri.car_app.dto.ResponseDto;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnerSaveToCarHandlerTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerSaveToCarHandler ownerSaveToCarHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenHttpServletRequest_whenAddOwnerToCar_thenReturnsResponseDto() throws IOException {
        ResponseDto responseDto = new ResponseDto(true);
        String vincode = "VF1KMSA1S00772001";
        String uuid = "212753d4-1570-479b-9045-82a8c6fa79b5";

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("vin")).thenReturn(vincode);
        when(request.getParameter("uuid")).thenReturn(uuid);
        when(ownerService.addOwnerToCar(vincode, uuid)).thenReturn(responseDto);

        ByteArrayOutputStream responseContent = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new DelegatingServletOutputStream(responseContent);
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        ownerSaveToCarHandler.handleRequest(request, response);

        String expectedJson = objectMapper.writeValueAsString(responseDto);
        String actualJson = responseContent.toString();

        verify(ownerService).addOwnerToCar("VF1KMSA1S00772001", "212753d4-1570-479b-9045-82a8c6fa79b5");

        assertThat(actualJson).isEqualTo(expectedJson);
    }
}
