package com.cedacri.car_app.controller.owner;

import com.cedacri.car_app.controller.owner_request_handlers.OwnerSaveHandler;
import com.cedacri.car_app.dto.OwnerDto;
import com.cedacri.car_app.services.OwnerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.DelegatingServletOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.cedacri.car_app.utils.OwnerDTOUtils.getPreparedOwnerDto;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OwnerSaveHandlerTest {

    @Mock
    private OwnerService ownerService;

    @Mock
    private Validator validator;

    @InjectMocks
    private OwnerSaveHandler ownerSaveHandler;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void givenHttpServletRequest_whenSaveCar_thenReturnsJson() throws IOException {
        OwnerDto requestDto = getPreparedOwnerDto();
        OwnerDto responseDto = getPreparedOwnerDto();

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        String jsonInput = objectMapper.writeValueAsString(requestDto);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(jsonInput.getBytes());
        ServletInputStream servletInputStream = new DelegatingServletInputStream(byteArrayInputStream);

        when(request.getInputStream()).thenReturn(servletInputStream);
        when(ownerService.saveOwner(requestDto)).thenReturn(responseDto);

        ByteArrayOutputStream responseContent = new ByteArrayOutputStream();
        ServletOutputStream servletOutputStream = new DelegatingServletOutputStream(responseContent);
        when(response.getOutputStream()).thenReturn(servletOutputStream);

        ownerSaveHandler.handleRequest(request, response);

        String expectedJson = objectMapper.writeValueAsString(responseDto);
        String actualJson = responseContent.toString();

        assertThat(actualJson).isEqualTo(expectedJson);
    }
}
